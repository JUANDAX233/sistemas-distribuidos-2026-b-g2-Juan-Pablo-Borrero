# CP2 - Structured Log Contract for the Sticker Journey

One JSON object per line on stdout, UTF-8, no multi-line stack traces (the stack goes in
`error_stack` as one string). It applies to `barber-saas-loyalty-api`, `barber-saas-appointment-api`,
`barber-saas-worker` and `barber-saas-notifications-api`. loyalty-api already prints ECS JSON
(`logging.structured.format.console: ecs`) with `correlationId` from the MDC; this contract adds
the trace fields and fixes the names below.

## Fields

| Name | Type | Required | Example | Why |
|---|---|---|---|---|
| `timestamp` | string, ISO-8601 UTC, ms | Required | `2026-10-19T15:04:05.120Z` | Orders lines across services without clock-zone guesses |
| `level` | enum `ERROR`/`WARN`/`INFO`/`DEBUG` | Required | `WARN` | Alert routing and filtering |
| `service` | string | Required | `loyalty-api` | Matches `spring.application.name` and the gateway upstream |
| `version` | string, semver | Required | `2.0.0` | Ties a line to the release in `CHANGELOG.md` |
| `environment` | enum `dev`/`staging`/`prod` | Required | `staging` | Keeps test traffic out of production searches |
| `trace_id` | string, 32 hex | Required | `5f2c9a7e41b0d3c86e1f04a29b7d58c3` | Joins every line of the journey (CP1, trace `T`) |
| `span_id` | string, 16 hex | Required | `9c41e07a2b5d13f6` | Places the line in the span tree |
| `correlation_id` | string | Required | `c-7d1e94a0` | The existing `X-Correlation-Id`; kept for the worker and old dashboards |
| `event` | string, dotted | Required | `loyalty.sticker.rejected` | Stable key to count and alert on; never free text |
| `message` | string | Required | `The appointment already has its sticker` | Human-readable summary |
| `duration_ms` | integer | Required | `3004` | Time of the unit the line closes; `0` for point-in-time lines |
| `barbershop_id` | UUID | Required in domain lines | `2a7f0c3e-...` | The tenant (`caller.tenant()`); every incident is scoped to one barbershop first |
| `appointment_id` | UUID or null | Optional | `1d7c2e9f-...` | The visit named in `GrantStickerRequest`; links to appointment-api lines |
| `card_id` / `transaction_id` | UUID | Optional | `b3e1...` / `e90a...` | Locate the `loyalty_card` and `loyalty_transaction` rows touched |
| `outcome` | enum `granted`/`retried`/`rejected`/`not_found`/`failed` | Required on `request.completed` | `rejected` | Same values as the CP3 counter label |
| `error_code` | enum from `ApiError` | Optional | `BUSINESS_RULE_VIOLATION` | Same code the client receives in `ErrorResponse` |
| `dependency` | string | Optional | `appointment-api` | Which downstream failed, for the CP9 slow-dependency drill |
| `http_status` | integer | Required on `request.completed` | `422` | Status the caller received |
| `actor_role` | enum `ADMIN_BARBERSHOP`/`BARBER` | Optional | `BARBER` | Who granted, checked against `INV-LOYAL-002` without logging the user |
| `idempotency_key_hash` | string, 12 hex | Optional | `a41f9be02c7d` | Proves two attempts were the same grant without logging the key |

## Never log

| Data in this domain | Where it appears | How it is handled |
|---|---|---|
| Bearer JWT of the barber | `Authorization` header, `caller.credential()` | Dropped; only `actor_role` (`BARBER`) is logged |
| Worker service token (`WORKER_SERVICE_TOKEN`) | Calls to `/internal/v1/*` | Dropped; log `sub` = `barber-saas-worker` only |
| `Idempotency-Key` value | Header of `POST /api/v1/loyalty/stickers` | First 12 hex of SHA-256 as `idempotency_key_hash` |
| `clientId` | Request body, `loyalty_card.client_id` | Keyed hash `client_ref` (HMAC, rotated per environment); the raw UUID stays in the database |
| Client name, email, phone | identity-auth data, never needed here | Not read by loyalty; if a payload carries them, the logger redacts to `***` |
| Notification text and device tokens | notifications-api, `POST /api/v1/device-tokens` | Log `notification_type` (`SYSTEM`) and `sourceEventId` only |
| `DATABASE_PASSWORD`, `DATABASE_URL` credentials | Startup configuration | Never printed; the URL is logged without user info |

## Example: one trace, two attempts

The shell retries the same tap once after a `500`, keeping the trace and opening a new span. The
appointment had already earned its automatic sticker through `AppointmentCompleted`
(`DEC-LOY-05`), so the second attempt is rejected by `uq_loyalty_transaction_sticker_per_appointment`.

```json
{"timestamp":"2026-10-19T15:04:02.101Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"staging","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"9c41e07a2b5d13f6","correlation_id":"c-7d1e94a0","event":"loyalty.sticker.requested","message":"Grant sticker requested","duration_ms":0,"barbershop_id":"2a7f0c3e-5b1d-4e8a-9c2f-6d3b8a1e7f40","appointment_id":"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f","idempotency_key_hash":"a41f9be02c7d","actor_role":"BARBER"}
{"timestamp":"2026-10-19T15:04:05.108Z","level":"ERROR","service":"loyalty-api","version":"2.0.0","environment":"staging","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"3e7b20c9d4a1f586","correlation_id":"c-7d1e94a0","event":"dependency.timeout","message":"appointment-api too slow to answer GET /api/v1/appointments/1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f","duration_ms":3004,"barbershop_id":"2a7f0c3e-5b1d-4e8a-9c2f-6d3b8a1e7f40","dependency":"appointment-api","error_code":"INTERNAL_ERROR"}
{"timestamp":"2026-10-19T15:04:05.111Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"staging","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"9c41e07a2b5d13f6","correlation_id":"c-7d1e94a0","event":"request.completed","message":"POST /api/v1/loyalty/stickers answered 500","duration_ms":3010,"barbershop_id":"2a7f0c3e-5b1d-4e8a-9c2f-6d3b8a1e7f40","outcome":"failed","http_status":500}
{"timestamp":"2026-10-19T15:04:06.420Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"staging","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"b81d4f0e6a93c27d","correlation_id":"c-7d1e94a0","event":"loyalty.sticker.requested","message":"Grant sticker requested","duration_ms":0,"barbershop_id":"2a7f0c3e-5b1d-4e8a-9c2f-6d3b8a1e7f40","appointment_id":"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f","idempotency_key_hash":"a41f9be02c7d","actor_role":"BARBER"}
{"timestamp":"2026-10-19T15:04:06.468Z","level":"INFO","service":"appointment-api","version":"2.0.0","environment":"staging","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"47f2a9c1e0b36d85","correlation_id":"c-7d1e94a0","event":"request.completed","message":"GET /api/v1/appointments/{id} answered 200","duration_ms":38,"barbershop_id":"2a7f0c3e-5b1d-4e8a-9c2f-6d3b8a1e7f40","appointment_id":"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f","http_status":200}
{"timestamp":"2026-10-19T15:04:06.495Z","level":"WARN","service":"loyalty-api","version":"2.0.0","environment":"staging","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"b81d4f0e6a93c27d","correlation_id":"c-7d1e94a0","event":"loyalty.sticker.rejected","message":"The appointment already has its sticker","duration_ms":0,"barbershop_id":"2a7f0c3e-5b1d-4e8a-9c2f-6d3b8a1e7f40","appointment_id":"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f","error_code":"BUSINESS_RULE_VIOLATION"}
{"timestamp":"2026-10-19T15:04:06.498Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"staging","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"b81d4f0e6a93c27d","correlation_id":"c-7d1e94a0","event":"request.completed","message":"POST /api/v1/loyalty/stickers answered 422","duration_ms":78,"barbershop_id":"2a7f0c3e-5b1d-4e8a-9c2f-6d3b8a1e7f40","outcome":"rejected","http_status":422}
```

## Levels

| Level | When | Example in this project |
|---|---|---|
| `ERROR` | The service could not do its job and a person may need to act: a dependency failed or the outbox could not be written. `request.completed` stays `INFO` and carries the `5xx` in `http_status` | `dependency.timeout` when `JsonApi` gives up on appointment-api after 3 s |
| `WARN` | The request was refused or degraded by design, and a growing rate means something is wrong | `loyalty.sticker.rejected` with `BUSINESS_RULE_VIOLATION`; the worker marking a `StickerGranted` row `failed` after 8 attempts |
| `INFO` | One line per business fact or finished request, enough to rebuild the journey | `loyalty.sticker.granted` with `card_id`, `transaction_id` and `stickers_count`; `request.completed` |
| `DEBUG` | Detail for a local reproduction, off in `staging` and `prod` | The `idempotency_key` lookup result before `saveSticker`, or the SQL time of the `loyalty_card` read |
