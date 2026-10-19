# CP4 - Golden Signals and Metric Catalog

The metrics of the sticker journey (HU-LOY-001), one table per service. Names are snake_case with
the unit as suffix and `_total` on counters. The two `loyalty_sticker_*` metrics and
`loyalty_dependency_request_duration_seconds` (outcome `found`, `not_found`, `failed`) are the ones
`03-instrumentation/loyalty-api` already exposes on `/metrics`; the rest is the target catalog.

## barber-saas-api-gateway (NGINX, edge hop)

| Signal | Metric | Type | Labels | Dashboard PromQL |
|---|---|---|---|---|
| Latency | `gateway_request_duration_seconds` | Histogram | `route`, `method` | `histogram_quantile(0.95, sum by (le) (rate(gateway_request_duration_seconds_bucket{route="loyalty"}[5m])))` |
| Traffic | `gateway_requests_total` | Counter | `route`, `method`, `status_class` | `sum by (route) (rate(gateway_requests_total[5m]))` |
| Errors | `gateway_requests_total` | Counter | `status_class="5xx"`, plus `429` from `limit_req zone=per_client` | `sum(rate(gateway_requests_total{route="loyalty",status_class="5xx"}[5m])) / sum(rate(gateway_requests_total{route="loyalty"}[5m]))` |
| Saturation | `gateway_connections_active` | Gauge | none | `max(gateway_connections_active) / 1024` (`worker_connections 1024` in `nginx.conf`) |

The gateway values come from its `log_format json` access line through a log exporter; `route` is
the routes file (`loyalty`, `appointment`, ...), not the URI.

## barber-saas-loyalty-api (central service)

| Signal | Metric | Type | Labels | Dashboard PromQL |
|---|---|---|---|---|
| Latency | `loyalty_sticker_request_duration_seconds` | Histogram | none | `histogram_quantile(0.95, sum by (le) (rate(loyalty_sticker_request_duration_seconds_bucket[5m])))` |
| Traffic | `loyalty_sticker_requests_total` | Counter | `outcome` | `sum(rate(loyalty_sticker_requests_total[5m]))` |
| Errors | `loyalty_sticker_requests_total` | Counter | `outcome="failed"` | `sum(rate(loyalty_sticker_requests_total{outcome="failed"}[5m])) / sum(rate(loyalty_sticker_requests_total[5m]))` |
| Saturation | `hikaricp_connections_pending` / `hikaricp_connections_active` | Gauge | `pool` | `max(hikaricp_connections_active) / 10` (`DB_POOL_MAX` 10) and `max(hikaricp_connections_pending) > 0` |
| Dependency | `loyalty_dependency_request_duration_seconds` | Histogram | `dependency`, `outcome` | `histogram_quantile(0.95, sum by (le) (rate(loyalty_dependency_request_duration_seconds_bucket{dependency="appointment-api"}[5m])))` |

`outcome` holds `granted`, `retried`, `rejected`, `not_found`, `failed`: `rejected` is the `422` of
`uq_loyalty_transaction_sticker_per_appointment` and is not counted as an error, because the rule
worked. The dependency histogram is the one the CP9 drill watches when appointment-api slows down.

## barber-saas-appointment-api (synchronous dependency)

| Signal | Metric | Type | Labels | Dashboard PromQL |
|---|---|---|---|---|
| Latency | `appointment_get_duration_seconds` | Histogram | none | `histogram_quantile(0.99, sum by (le) (rate(appointment_get_duration_seconds_bucket[5m])))` |
| Traffic | `appointment_get_requests_total` | Counter | `outcome` (`found`, `not_found`, `failed`) | `sum(rate(appointment_get_requests_total[5m]))` |
| Errors | `appointment_get_requests_total` | Counter | `outcome="failed"` | `sum(rate(appointment_get_requests_total{outcome="failed"}[5m])) / sum(rate(appointment_get_requests_total[5m]))` |
| Saturation | `tomcat_threads_busy_threads` | Gauge | none | `max(tomcat_threads_busy_threads) / 200` (`server.tomcat.threads.max`) |

Only `GET /api/v1/appointments/{id}` is in this journey; `not_found` covers another barbershop's id,
which answers `404` by design.

## barber-saas-worker (asynchronous relay, ADR-016)

| Signal | Metric | Type | Labels | Dashboard PromQL |
|---|---|---|---|---|
| Latency | `worker_event_delivery_lag_seconds` | Histogram | `event_type` | `histogram_quantile(0.95, sum by (le) (rate(worker_event_delivery_lag_seconds_bucket{event_type="StickerGranted"}[15m])))` |
| Traffic | `worker_events_delivered_total` | Counter | `event_type`, `consumer`, `outcome` | `sum by (event_type) (rate(worker_events_delivered_total[5m]))` |
| Errors | `worker_events_delivered_total` | Counter | `outcome="failed"` (a `4xx` or 8 attempts) | `sum(increase(worker_events_delivered_total{outcome="failed"}[1h]))` |
| Saturation | `worker_outbox_pending_events` | Gauge | `producer` | `max by (producer) (worker_outbox_pending_events{producer="loyalty"})` against the 50 events per run |

The lag is `now - occurred_at` of the `outbox_event` row when the consumer answers `2xx`: what the
client feels between the sticker and the "Ganaste un sello" notification.

## barber-saas-notifications-api (consumer)

| Signal | Metric | Type | Labels | Dashboard PromQL |
|---|---|---|---|---|
| Latency | `notifications_event_handling_duration_seconds` | Histogram | `event_type` | `histogram_quantile(0.95, sum by (le) (rate(notifications_event_handling_duration_seconds_bucket[5m])))` |
| Traffic | `notifications_events_received_total` | Counter | `event_type`, `outcome` (`created`, `duplicate`, `refused`) | `sum by (event_type) (rate(notifications_events_received_total[5m]))` |
| Errors | `notifications_events_received_total` | Counter | `outcome="refused"` | `sum(rate(notifications_events_received_total{outcome="refused"}[5m]))` |
| Saturation | `notifications_mongo_pool_in_use` | Gauge | none | `max(notifications_mongo_pool_in_use) / max(notifications_mongo_pool_size)` |

`duplicate` is a redelivery stopped by the unique `sourceEventId`; a rising rate means the worker
is not getting the `2xx` back, not that notifications is failing.

## Label cardinality

| Label | Values | Estimate |
|---|---|---|
| `outcome` | fixed per metric | 3-5 |
| `event_type` | the `ROUTES` table of `worker/domain/model/routing.py` | 9 |
| `producer` | appointment, identity-auth, loyalty | 3 |
| `consumer` / `dependency` | loyalty, notifications / appointment-api | 2 / 1 |
| `route` | one per file in `nginx/routes/` | 10 |
| `method`, `status_class` | GET, POST, PUT, PATCH, DELETE / 2xx-5xx | 5 / 4 |

The largest series, `gateway_requests_total`, stays under 10 x 5 x 4 = 200 series per instance.

**Forbidden labels and what replaces them:**

| Unbounded value | Why it is forbidden | Replacement |
|---|---|---|
| `clientId`, `user_id`, `grantedByUserId` | One series per person; also personal data | `client_ref` in the logs of CP2 |
| `barbershop_id` | Grows with every tenant that signs up | `barbershop_id` field in logs; per-tenant numbers come from queries, not metrics |
| `appointment_id`, `card_id`, `transaction_id`, `trace_id` | One series per request | Log fields; `trace_id` as a histogram exemplar |
| Raw URL (`/api/v1/appointments/1d7c2e9f-...`) | The id inside the path | The route template or the routes file name |
| Email, phone, `Idempotency-Key` | Unbounded and sensitive | Never recorded; `idempotency_key_hash` stays in logs only |

## Histogram buckets

`loyalty_sticker_request_duration_seconds` uses 0.05, 0.1, 0.25, 0.5, 1, 2.5 and 5 seconds. The
barber grants the sticker with the client still at the chair, so the confirmation should feel
immediate: under 0.5 s is the target (p95), and 1 s is where the barber starts to tap again,
which is the retry of CP2. The fine buckets below 0.5 s show regressions inside the target;
2.5 s and 5 s bracket the 3 s timeout of `AppointmentApiClient`, so a slow appointment-api moves
requests from the `le="1"` bucket into `le="5"` and the CP9 drill is visible without new buckets.

`worker_event_delivery_lag_seconds` uses 1, 5, 10, 30, 60 and 300 seconds: the worker polls every
5 s, so the normal lag sits between 1 and 10 s, and a client accepts a notification arriving within
a minute; 300 s marks events stuck in retries.
