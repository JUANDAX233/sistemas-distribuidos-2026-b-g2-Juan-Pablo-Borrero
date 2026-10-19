# CP7 - Alert Policy for the Sticker Journey

The rules live in [`06-alerts.yml`](06-alerts.yml) and their unit tests in
[`06-alerts.test.yml`](06-alerts.test.yml). They alert on the symptoms the barber or the client
feels (SLO-LOY-1, -2 and -3 of `05-slo.md`), never on a cause alone: a slow
`barber-saas-appointment-api` matters only when it makes `POST /api/v1/loyalty/stickers` fail
or slow down.

## Burn-rate thresholds

Burn rate = observed bad ratio / budget ratio. Each alert needs a long window (is the budget
really going?) and a short one (is it still going now?), so it fires fast and clears fast.

| Alert | SLO | Burn | Long / short window | Threshold (bad ratio) | Budget spent when it fires | Budget gone in | Severity |
|---|---|---|---|---|---|---|---|
| `LoyaltyStickerErrorBudgetFastBurn` | LOY-1 | 14.4x | 1 h / 5 min | 14.4 x 0.005 = 7.2 % failed | 2 % in 1 h | 2.1 days | page |
| `LoyaltyStickerErrorBudgetSlowBurn` | LOY-1 | 6x | 6 h / 30 min | 6 x 0.005 = 3 % failed | 5 % in 6 h | 5 days | page |
| `LoyaltyStickerErrorBudgetDailyBurn` | LOY-1 | 3x | 1 d / 2 h | 3 x 0.005 = 1.5 % failed | 10 % in 1 day | 10 days | ticket |
| `LoyaltyStickerLatencyFastBurn` | LOY-2 | 14.4x | 1 h / 5 min | 14.4 x 0.05 = 72 % slower than 0.5 s | 2 % in 1 h | 2.1 days | page |
| `LoyaltyStickerLatencyDailyBurn` | LOY-2 | 3x | 1 d / 2 h | 3 x 0.05 = 15 % slower than 0.5 s | 10 % in 1 day | 10 days | ticket |
| `StickerGrantedDeliveryLate` | LOY-3 | 3x | 1 d / 2 h | 3 x 0.01 = 3 % later than 60 s | 10 % in 1 day | 10 days | ticket |

"Budget gone in" is 30 days / burn rate. The error ratio is always `outcome="failed"` over all
grants: `rejected` (the `422` of `uq_loyalty_transaction_sticker_per_appointment`) and
`not_found` are correct answers and never count.

## Low traffic

`05-slo.md` estimates 1.5 grants per minute, and fewer at opening time. With so few requests a
5-minute window holds a handful of grants, and one failed tap alone is 14-20 % failed, above
the 7.2 % threshold. The two paging rules of SLO-LOY-1 and the fast latency rule therefore
also require a minimum volume in the long window: 20 grants in 1 h, 60 in 6 h. The second test
of `06-alerts.test.yml` proves it: 12 grants in an hour with half of them failed do not page.

The price: in the quietest hours an outage pages later, or not at all, and the daily ticket
(`LoyaltyStickerErrorBudgetDailyBurn`, no volume guard) is what catches it. The budget is
spent in requests, so quiet hours also spend little of it.

## Severity and routing

| Severity | Meaning | Who | When | Channel |
|---|---|---|---|---|
| `page` | The journey is failing for barbers now; act within 15 minutes | Owner of the `service` label | Opening hours, 08:00-20:00 Monday to Saturday (`05-slo.md`) | Phone call, then the team chat |
| `ticket` | The budget is going down slowly or the client hears late | Owner of the `service` label | Next working day | GitHub issue in `code-corhuila/barber-saas-docs` with the alert name |

Owners from `09-microservices/service-catalog.md`:

| `service` / dependency | Owner | Role in the journey |
|---|---|---|
| `loyalty-api` | Juan Pablo Borrero | Central service; receives every page of this file |
| `appointment-api` | Juan Pablo Borrero | Synchronous dependency, usual cause of `dependency.timeout` |
| `barber-saas-worker`, `barber-saas-api-gateway`, `infra-postgres` | Daniel Cerquera | Asynchronous relay, edge and database |
| `notifications-api` | Carlos Leal (Tech Lead) | Consumer of `StickerGranted` |

A page outside opening hours is not expected: no grants, no budget burn. If one fires at night,
the first suspect is test traffic or a clock problem, not barbers.

## Error budget policy

Taken from NFR-002 (`04-requirements/non-functional.md`) and applied to SLO-LOY-1:

| Budget spent in the 30-day window | What changes |
|---|---|
| < 50 % | Normal work; features ship |
| > 50 % in the first 15 days | New features of loyalty-api and appointment-api wait; fixes and tests for the failing path go first |
| 100 % | Only reliability changes merge until the window recovers; the postmortem of `08-incident.md` lists the action items |

## Why each rule has these labels

- `severity` routes it, `service` and `owner` say who acts, `slo` links it to `05-slo.md`.
- `runbook_url` points to the section of `07-runbook.md` with the same number as the alert.
- `summary` carries the measured value with `humanizePercentage`, so the page already says how bad it is.

## Validation

```text
> promtool check rules 06-alerts.yml
Checking 06-alerts.yml
  SUCCESS: 18 rules found

> promtool test rules 06-alerts.test.yml
Unit Testing:  06-alerts.test.yml
  SUCCESS
```

Prometheus 2.53.5 for Windows (`promtool, version 2.53.5`, revision `d344ea7`), downloaded to a
temporary folder outside the repository and checked against the release's `sha256sums.txt`.
The three tests: 10 % failed at 3 grants per minute fires the fast burn with the expected
labels and annotations; the low-traffic case does not; a healthy 0.5 % fires neither page.

## What exists today

- `barber-saas-infra-postgres` has Prometheus, Grafana and the OpenTelemetry collector under the
  `observability` profile (commit `e7cea59`), but `prometheus.yml` only scrapes `otel-collector`,
  loads no `rule_files` and has no Alertmanager. None of these rules runs yet.
- The worker exports no `worker_event_delivery_lag_seconds`; `StickerGrantedDeliveryLate`
  waits for it.
