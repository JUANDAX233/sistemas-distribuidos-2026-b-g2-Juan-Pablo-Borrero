# CP8 - Runbook: Sticker Journey (HU-LOY-001)

One section per alert of `06-alerts.yml`; each `runbook_url` lands on its number. Commands run
from the root of `barber-saas-infra-postgres`, which composes every repository, with the
environment file of the affected environment (`env/dev.env` below).

## Owners and escalation

| Component | Owner (`09-microservices/service-catalog.md`) | Escalate when |
|---|---|---|
| `barber-saas-loyalty-api`, `barber-saas-appointment-api` | Juan Pablo Borrero | First responder for every alert here |
| `barber-saas-worker`, `barber-saas-api-gateway`, `barber-saas-infra-postgres` | Daniel Cerquera | The relay is stuck, the gateway answers `5xx` by itself, or PostgreSQL is down |
| `barber-saas-notifications-api` | Carlos Leal (Tech Lead) | `POST /internal/v1/events` refuses `StickerGranted`, or no owner answers in 15 min |

## Mitigation levers that exist today

| Lever | How | Limits |
|---|---|---|
| Scale out | `docker compose --env-file env/dev.env up -d --no-deps --scale appointment-api=2 appointment-api` | Works because each `deploy/compose.yml` uses `expose` and no `container_name`, and the gateway resolves its upstream by name (`resolver 127.0.0.11 valid=10s`), so a new replica gets traffic within 10 s. Helps only if the instance is saturated, not if every call is slow |
| Restart | `docker compose --env-file env/dev.env restart appointment-api` | Manual only: no compose file declares a `restart:` policy, so a crashed container stays down until someone runs this |
| Roll back | `git -C ../barber-saas-appointment-api checkout v2.0.0`, then `docker compose --env-file env/dev.env up -d --build --no-deps appointment-api` | Images are built locally with `build:`; no registry keeps older images. Putting the rollback on `main` needs a pull request approved by `@ariel5253` (`CODEOWNERS`) |
| Grant without the visit | The barber leaves the appointment out: `appointmentId` is optional (`DEC-LOY-01`), so loyalty-api never calls appointment-api | The sticker is no longer audited against the visit; the automatic sticker of `AppointmentCompleted` (`DEC-LOY-05`) still credits it once |

Not available: no feature flag to switch off the appointment check, and no circuit breaker in
`JsonApi` - every grant still waits up to 3 s for appointment-api while it is slow.

## First five minutes (every alert)

1. Acknowledge in the team chat with the alert name and the time.
2. Look at the dashboard of `04-metrics.md`:
   `sum by (outcome) (rate(loyalty_sticker_requests_total[5m]))` and
   `histogram_quantile(0.95, sum by (le) (rate(loyalty_dependency_request_duration_seconds_bucket{dependency="appointment-api"}[5m])))`.
3. Check the containers: `docker compose --env-file env/dev.env ps loyalty-api appointment-api worker notifications-api`.
4. Pick one failed request and follow its `trace_id` through every service:
   `docker compose --env-file env/dev.env logs --since 15m loyalty-api appointment-api | grep <trace_id>`.

## 1. LoyaltyStickerErrorBudgetFastBurn

More than 7.2 % of grants answer `500` for an hour.

| If | Then |
|---|---|
| loyalty-api logs show `too slow to answer GET /api/v1/appointments/` | appointment-api is slow: go to step A |
| appointment-api is not `healthy` in `ps` | Restart it; if it falls again, roll back to `v2.0.0` |
| Errors without a dependency message | loyalty-api itself or `loyalty-db`: check `docker compose logs postgres` and the pool (`DB_POOL_MAX` 10) |

Step A: compare appointment-api's own latency with what loyalty-api sees. If appointment-api is
slow for every caller, check its database queries and a recent deploy (`git -C ../barber-saas-appointment-api log -3`),
then scale out or roll back. Meanwhile, tell the barbers to grant without the visit.

Resolved when the 5-minute failed ratio stays under 0.5 % for 15 minutes.

## 2. LoyaltyStickerErrorBudgetSlowBurn

More than 3 % of grants fail for 6 hours: a partial failure (one barbershop, one replica,
intermittent timeouts). Group the failed logs by `barbershop_id` and by container; if one
replica fails, restart only that one with `docker restart <container id from ps>`.

## 3. LoyaltyStickerErrorBudgetDailyBurn

Ticket. Open an issue in `code-corhuila/barber-saas-docs` with the failed ratio of the day, the
most frequent `event` among the failed lines, and whether the `422` rejections changed (they do
not burn budget, but a jump means the automatic and manual stickers collide more often).

## 4. LoyaltyStickerLatencyFastBurn

Most grants take more than 0.5 s but do not fail. If the dependency p95 is close to 3 s,
appointment-api is the cause: same as section 1, step A. If the dependency is fast, look at
`loyalty-db`: a slow `saveSticker` transaction or a full pool.

## 5. LoyaltyStickerLatencyDailyBurn

Ticket. Attach the p50/p95/p99 of `loyalty_sticker_request_duration_seconds` and of the
dependency histogram for the day; propose the fix as a story with acceptance criteria.

## 6. StickerGrantedDeliveryLate

Ticket for Daniel Cerquera (worker). The sticker is saved; only the "Ganaste un sello"
notification is late. Check that `worker` is `healthy`, that `GET /internal/v1/outbox-events`
on loyalty-api answers, and the rows waiting in `loyalty.outbox_event` with
`published_at IS NULL AND failed_at IS NULL`. Rows with `failed_at` set (a `4xx` or 8 attempts,
ADR-016) need Carlos Leal: notifications-api refused them, and `last_error` says why.

## After the incident

Write the postmortem in the format of `08-incident.md` within two working days, and turn each
action item into a story with Given/When/Then in `barber-saas-docs`.
