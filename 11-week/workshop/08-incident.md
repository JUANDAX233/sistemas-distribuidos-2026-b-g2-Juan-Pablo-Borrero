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

## Postmortem

Blameless format. The incident is the drill above, placed at the Saturday midday peak; the cause
was injected, so "root cause" names what the drill proves about the system, not a person.

**Summary.** For 50 minutes appointment-api answered slowly. For the first 30 minutes every
sticker took 2 s and no alert fired; for the last 20 every sticker failed with `500` after 3 s,
and the first page came 7 minutes into that phase.

**Impact.**

| Measure | Value | Share of the 30-day budget (`05-slo.md`) |
|---|---|---|
| Failed grants (SLO-LOY-1) | 90 | 90 / 136 = **66 %** in 20 minutes |
| Grants slower than 0.5 s (SLO-LOY-2) | 225 | 225 / 1,365 = **16.5 %** |
| Barbers affected | Every barber of every barbershop granting with the visit | - |
| Stickers lost | 0 once the barber repeats the grant, or later through `AppointmentCompleted` (`DEC-LOY-05`) | - |

The replay rounds the traffic to 1.5 grants per minute (1,080 a day) where `05-slo.md` uses
1,050; the shares above use the budget of `05-slo.md` and are about 3 % pessimistic.

**Timeline (Saturday).**

| Time | Event |
|---|---|
| 11:30 | appointment-api starts answering in 2 s. Grants succeed, slowly. `loyalty_dependency_request_duration_seconds` p95 jumps to 2 s; nothing alerts on it |
| 12:00 | appointment-api passes 3 s. Every grant with an `appointmentId` fails with `500`; `dependency.timeout` lines start |
| 12:07 | `LoyaltyStickerErrorBudgetFastBurn` pages Juan Pablo Borrero (11.67 % failed over 1 h) |
| 12:10 | Runbook section 1: the `trace_id` of a failed grant shows the 3 s wait on `GET /api/v1/appointments/{id}`. Barbers are told to grant without the visit |
| 12:20 | appointment-api restored. `LoyaltyStickerLatencyFastBurn` pages anyway: its 1-hour window still holds the slow grants |
| 12:25 | Fast burn clears |
| 12:50 | Slow burn clears; incident closed |

**Root cause.** loyalty-api depends synchronously on appointment-api for every grant that names a
visit, with a 3 s timeout and no fallback. Below 3 s the dependency's latency passes straight to
the barber; above 3 s it becomes a failure.

**Findings.**

1. **Thirty minutes of 100 % slow grants fired nothing.** With uniform traffic the 1-hour slow
   ratio reaches the 72 % threshold of `LoyaltyStickerLatencyFastBurn` only after 0.72 x 60 =
   43.2 minutes, plus its 2-minute `for`: about 45 minutes. Had appointment-api recovered at
   12:00, the 135 slow grants (9.9 % of the latency budget) would have left no alert, and the
   daily ticket would not fire either (10.7 % against 15 %).
2. **The timeout answers `500 INTERNAL_ERROR`.** The barber's app cannot tell "loyalty-api is
   broken" from "try again in a minute"; a `503` would say it.
3. **No circuit breaker.** Each failed grant waits the full 3 s. At 4.5 grants per minute that
   holds 4.5 x 3 / 60 = 0.23 Tomcat threads on average out of 200, so nothing saturates at this
   traffic; the cost is the barber's time, not capacity.
4. **The pages would not reach anybody today.** `barber-saas-infra-postgres` runs Prometheus
   without `rule_files` and without Alertmanager (`06-alert-policy.md`); in the real platform the
   first signal would be a barber's complaint.
5. **Alerts outlive the fix.** The latency page arrived when the problem was already gone, and
   the slow burn stayed 30 minutes after it; the runbook's "resolved when" criteria are needed.

**What went well.** One `trace_id` led from the failed grant to the slow downstream call in one
search; the `outcome` label kept `rejected` and `not_found` out of the error budget; the volume
guard was not in the way at midday traffic.

## Action items

**AI-1 - Early warning on the dependency** (owner: Juan Pablo Borrero, `barber-saas-loyalty-api`)
```gherkin
Given the p95 of loyalty_dependency_request_duration_seconds{dependency="appointment-api"} is above 1 s
When it stays there for 10 minutes with at least 20 grants
Then a ticket alert LoyaltyAppointmentDependencySlow reaches the owner of appointment-api
```

**AI-2 - A timeout is a 503** (owner: Juan Pablo Borrero, `barber-saas-loyalty-api`)
```gherkin
Given appointment-api does not answer GET /api/v1/appointments/{id} within 3 s
When a barber grants a sticker with that appointmentId
Then loyalty-api answers 503 with Retry-After and the grant is counted with outcome="failed"
```

**AI-3 - Fail fast while appointment-api is down** (owner: Juan Pablo Borrero)
```gherkin
Given five grants in a row ended in dependency.timeout within one minute
When another grant names an appointment in the next 30 seconds
Then loyalty-api answers 503 in less than 100 ms without calling appointment-api
And one grant after the 30 seconds probes appointment-api again
```

**AI-4 - Load the rules** (owner: Daniel Cerquera, `barber-saas-infra-postgres`)
```gherkin
Given the observability profile of barber-saas-infra-postgres starts
When Prometheus loads its configuration
Then rule_files includes the rules of 06-alerts.yml, Alertmanager routes severity=page to the owner,
And CI runs promtool check rules and promtool test rules on every pull request
```
