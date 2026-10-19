# CP6 - SLOs for the Sticker Journey

Journey: a `BARBER` grants a sticker (HU-LOY-001) through `barber-saas-api-gateway` to
`POST /api/v1/loyalty/stickers` in `barber-saas-loyalty-api`, which checks the visit with
`GET /api/v1/appointments/{id}` in `barber-saas-appointment-api`; `StickerGranted` then leaves
`loyalty.outbox_event` through `barber-saas-worker` to `barber-saas-notifications-api`.
The SLIs use only the metrics of `04-metrics.md`.

## Expected traffic

The journey happens at most once per completed appointment: the unique index
`uq_loyalty_transaction_sticker_per_appointment` allows one sticker per visit, and walk-ins
have no `clientId` to credit. So the volume follows completed visits, not bookings.

| Input | Value | Source |
|---|---|---|
| Barbershops | 50 paid subscriptions | `03-product/vision.md` KR1 (12 months after launch) |
| Barbers per shop | 3 (the middle of 2-4) | `03-product/problem-framing.md`, target segment |
| Completed visits per barber per day | 10 (assumption) | Not in the docs: about 40 min per service in a 10-hour working day, minus gaps |
| Visits of registered clients | 70 % | `vision.md` KR6 / `problem-framing.md`: > 70 % booked through the app |
| Open days | 6 per week, 26 in a 30-day window (25.7 rounded) | Assumption: Monday to Saturday |
| Opening hours | 12 per day, 08:00-20:00 | Assumption; the docs define no business hours |

- Stickers per day: 50 x 3 x 10 x 0.70 = **1,050** (upper bound).
- Per 30-day window: 1,050 x 26 = **27,300 requests**.
- Average rate in opening hours: 1,050 / (12 x 3,600 s) = **0.024 req/s**, about 1.5 per minute.
  With a 3x peak at midday, about 4-5 per minute.

`04-requirements/non-functional.md` NFR-001 still says "Minimum throughput: Not yet defined";
this estimate fills that gap for this journey only and should be replaced by the real
`loyalty_sticker_requests_total` once the service exports it.

## SLIs and SLOs

Window: **30 days, rolling.** It matches the "per month" figures of NFR-002 (99.9 %, 44
minutes). The team's `observability-plan.md` (week 11, Daniel Cerquera) uses 28 rolling days,
which keeps the same weekdays in every window; at this traffic the difference is two days, at most
2,100 requests, and changes no target.

| SLO | SLI (good / valid events) | Target | PromQL |
|---|---|---|---|
| SLO-LOY-1 Availability | Grants not answered with `outcome="failed"` / all grants | 99.5 % | `1 - sum(rate(loyalty_sticker_requests_total{outcome="failed"}[30d])) / sum(rate(loyalty_sticker_requests_total[30d]))` |
| SLO-LOY-2 Latency | Grants answered within 0.5 s / all grants | 95 % | `sum(rate(loyalty_sticker_request_duration_seconds_bucket{le="0.5"}[30d])) / sum(rate(loyalty_sticker_request_duration_seconds_count[30d]))` |
| SLO-LOY-3 Freshness | `StickerGranted` delivered to notifications-api within 60 s / all `StickerGranted` delivered | 99 % | `sum(rate(worker_event_delivery_lag_seconds_bucket{event_type="StickerGranted",le="60"}[30d])) / sum(rate(worker_event_delivery_lag_seconds_count{event_type="StickerGranted"}[30d]))` |

Why these numbers:

- **SLO-LOY-1, 99.5 % and not the 99.9 % of NFR-002.** NFR-002 is the availability of the
  whole production environment. The sticker endpoint is not in NFR-001's critical list (only
  booking and login are), a failed grant can be repeated with the same `Idempotency-Key`, and
  the automatic sticker of `AppointmentCompleted` (`DEC-LOY-05`) still credits the visit.
  `rejected` (the `422` of a duplicate sticker) and `not_found` are correct answers, so they
  count as good events.
- **SLO-LOY-2, 0.5 s.** The threshold is the `le="0.5"` bucket that `StickerMetrics`
  already registers, so the SLI is exact and not interpolated. It sits between NFR-001's
  300 ms for critical endpoints and 1000 ms for the rest: the client is still in the chair
  when the barber taps the grant. `failed` requests stay in the denominator, because a
  3-second timeout is slow for the barber too.
- **SLO-LOY-3, 60 s.** The worker polls every 5 s (ADR-016), so a healthy delivery lands in
  the 5 s or 10 s bucket. 60 s is the `le="60"` bucket and the point where the "Ganaste un
  sello" notification no longer arrives while the client is in the shop.

## Error budget

| SLO | Budget share | In events (30 days) | In minutes, whole window | In minutes, opening hours |
|---|---|---|---|---|
| SLO-LOY-1 | 0.5 % | 27,300 x 0.005 = **136 failed grants** | 43,200 x 0.005 = **216 min** | 18,720 x 0.005 = **93.6 min** |
| SLO-LOY-2 | 5 % | 27,300 x 0.05 = **1,365 slow grants** | 43,200 x 0.05 = **2,160 min** | 18,720 x 0.05 = **936 min** |
| SLO-LOY-3 | 1 % | 27,300 x 0.01 = **273 late events** | 43,200 x 0.01 = **432 min** | 18,720 x 0.01 = **187.2 min** |

Minutes: the whole window is 30 x 24 x 60 = 43,200 minutes; opening hours are
26 days x 12 h x 60 = 18,720 minutes.

**Whole window vs. opening hours.** The SLIs count requests, and grants only happen while the
shop is open. A loyalty-api outage at 03:00 burns no budget, while the same outage at 11:00
burns it at the full rate. Reading the budget as 216 minutes therefore overstates it more than
twice: in practice, loyalty-api can be fully down for about **93 minutes of opening time** per
window before SLO-LOY-1 is broken. NFR-002's maintenance window (Sundays 02:00-04:00) falls
outside opening hours and costs nothing.

**Low traffic.** At about 1.5 grants per minute, 136 failed grants is about 93 minutes of full
outage at the average rate, and less than 40 minutes at the midday peak. One
failed request is 1/27,300 of the window, so short bursts move the SLI visibly; the alerts of
CP7 need windows long enough to hold several requests.

## What exists today

- `loyalty_sticker_requests_total` and `loyalty_sticker_request_duration_seconds` are exposed
  only by `03-instrumentation/loyalty-api`, not by the team's `barber-saas-loyalty-api`.
- `worker_event_delivery_lag_seconds` is still a target of `04-metrics.md`; no worker exports it.
- No Prometheus scrapes either service yet, so every SLI above is defined, not measured.
