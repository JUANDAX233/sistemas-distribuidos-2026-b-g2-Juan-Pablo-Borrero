# CP9 - Incident Drill: appointment-api Answers Slowly

## Drill

**Hypothesis.** When `barber-saas-appointment-api` slows down, `POST /api/v1/loyalty/stickers`
slows down with it while `GET /api/v1/appointments/{id}` answers within the 3 s attempt timeout
of `AppointmentApiClient`, and fails with `500` once it does not. No retry happens after a
timeout, so each grant costs exactly one call and at most about 3 s.

**Setup (2026-10-19, local).** The CP3 service (`03-instrumentation/loyalty-api`, built with
`mvn package`) ran with `ENVIRONMENT=staging` and `APPOINTMENT_API_URL` pointing at a stand-in
for appointment-api: a 15-line Python `ThreadingHTTPServer` that answers `200` after a delay read
from a file, and prints the `traceparent` it receives. A shell loop sent grants one after the
other, each with its own `traceparent`, and changed the delay between phases.

| Phase | Delay of the stand-in | Grants | Answers | Mean time of the grant |
|---|---|---|---|---|
| Baseline | 0.04 s | 10 | 10 x `201` | 0.057 s |
| Slow | 2.0 s | 10 | 10 x `201` | 2.007 s |
| Timeout | 3.5 s | 10 | 10 x `500` | 3.009 s |
| Recovered | 0.04 s | 5 | 5 x `201` | 0.052 s |

The stand-in logged **35 calls for 35 grants**: no retry after a timeout, as `JsonApi` does in
`barber-saas-loyalty-api`. Every call carried the grant's `trace_id` under a new span id.

One slow and one timed-out grant, from the service log and the stand-in:

```text
{"timestamp":"2026-10-19T20:58:33.974Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"staging","trace_id":"615748adc3e0b4b4c00aba75709f14ac","span_id":"0ae593fbc3a95358","correlation_id":"3833aebf-45a0-43e0-86dd-b05375c868ed","event":"request.completed","message":"POST /api/v1/loyalty/stickers answered 201","duration_ms":2004,"http_status":201,"outcome":"granted"}
15:58:31 GET /api/v1/appointments/1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f traceparent=00-615748adc3e0b4b4c00aba75709f14ac-a52b57231dc574b6-01 delay=2.0
{"timestamp":"2026-10-19T20:58:58.405Z","level":"ERROR","service":"loyalty-api","version":"2.0.0","environment":"staging","trace_id":"2d4d71852a366f323de93d3bfad2678a","span_id":"33318f4cb2b86fe7","correlation_id":"df94ff61-159c-47a2-b41a-ca188017d5d6","event":"dependency.timeout","message":"appointment-api too slow to answer GET /api/v1/appointments/1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f","duration_ms":3001,"dependency":"appointment-api","error_code":"INTERNAL_ERROR"}
15:58:55 GET /api/v1/appointments/1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f traceparent=00-2d4d71852a366f323de93d3bfad2678a-4e1153982600f550-01 delay=3.5
```

`/metrics` after the 35 grants (excerpt):

```text
loyalty_sticker_requests_total{outcome="failed"} 10.0
loyalty_sticker_requests_total{outcome="granted"} 25.0
loyalty_sticker_request_duration_seconds_bucket{le="0.5"} 15
loyalty_sticker_request_duration_seconds_bucket{le="2.5"} 25
loyalty_sticker_request_duration_seconds_bucket{le="5.0"} 35
loyalty_dependency_request_duration_seconds_count{dependency="appointment-api",outcome="failed"} 10
loyalty_dependency_request_duration_seconds_max{dependency="appointment-api",outcome="failed"} 3.0017097
```

The hypothesis held: 2 s grants all succeed and all break SLO-LOY-2; 3.5 s grants all fail after
3.0 s and burn SLO-LOY-1.

## The same failure at production traffic

The drill ran 35 grants in two minutes, so it cannot fill a 1-hour window. The alerts of
`06-alerts.yml` were replayed with `promtool test rules 08-incident.test.yml` on the traffic of
`05-slo.md`, rounded to values exact in floating point: 1.5 grants per minute from 08:00 to
20:00 on the day before, 1.5 per minute from 08:00 and 4.5 per minute (the 3x midday peak)
from 11:00. appointment-api answers in 2 s from 11:30, in more than 3 s from 12:00, and is fixed
at 12:20.

Failed grants follow from the client's behaviour, not from a guess: under 3 s nothing fails;
over 3 s every grant with an `appointmentId` fails once, with no retry.

| From - to | appointment-api | Grants | Failed | Slower than 0.5 s |
|---|---|---|---|---|
| 11:30 - 12:00 | 2 s | 30 x 4.5 = 135 | 0 | 135 |
| 12:00 - 12:20 | > 3 s | 20 x 4.5 = 90 | **90** | 90 |
| **Total** | | 225 | **90** | **225** |

| Time | What the rules do (from `promtool`) |
|---|---|
| 12:00 | Nothing pending. After 30 minutes of 2 s grants, slow ratio 50 % over 1 h (threshold 72 %) and 10.7 % over 1 day (threshold 15 %) |
| 12:04-12:05 | `LoyaltyStickerErrorBudgetSlowBurn`, `FastBurn` and `DailyBurn` go pending; failed ratio over 1 h 8.3 % at 12:05 |
| **12:07** | **`LoyaltyStickerErrorBudgetFastBurn` pages** (11.67 % over 1 h) |
| 12:15 | `LoyaltyStickerLatencyFastBurn` and `LatencyDailyBurn` go pending |
| 12:20 | `LoyaltyStickerLatencyFastBurn` pages (83.33 % over 1 h); `SlowBurn` pages after its 15 minutes |
| 12:25 | `FastBurn` clears: five clean minutes after the fix |
| 12:40 - 12:50 | `SlowBurn` still firing at 12:40 (12.5 % over 6 h), clear by 12:50 when the failures leave its 30-minute window |
