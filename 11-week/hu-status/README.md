<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       11-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 11

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Present release `v2.0.0` with the team (2026-10-15), and in the week-11 workshop make one journey of my domains fully observable - a barber granting a loyalty sticker (HU-LOY-001) through `loyalty-api` and `appointment-api` - from trace context and structured logs to SLOs, burn-rate alerts, a runbook and a slow-dependency drill, each part tested or checked with `promtool`.
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| REL-2.0.0 | Release 2.0.0 (MVP 2) of my 9 repositories, shown in the final presentation | done (merged 2026-10-09, presented 2026-10-15) | appointment-api [#23](https://github.com/code-corhuila/barber-saas-appointment-api/pull/23) · loyalty-api [#19](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/19) · finance-inventory-api [#18](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/18) · [DOCS #109](https://github.com/code-corhuila/barber-saas-docs/issues/109) · deck [`Sustentacion-BarberSaaS.pdf`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/Sustentacion-BarberSaaS.pdf) |
| W11-WS-1 | As the owner of loyalty-api, I want the sticker journey mapped, logged, instrumented and measured, so one `trace_id` leads from a failed grant to the hop that failed (workshop delivery 1: CP0-CP5) | done | CP0 [`8918c9f`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/8918c9f), [`7ead81a`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/7ead81a), [`e79513a`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e79513a) · CP1 [`f83bde4`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f83bde4) · CP2 [`166d40f`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/166d40f) · CP3 [`e57f2c7`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e57f2c7), [`d09ff8b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/d09ff8b), [`34cd921`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/34cd921), [`ff47acb`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/ff47acb), [`1a15fd5`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/1a15fd5) · CP4 [`8a44d87`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/8a44d87) · CP5 [`141c1a6`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/141c1a6) |
| W11-WS-2 | As the owner of loyalty-api, I want SLOs, alerts, a runbook and a rehearsed incident for the sticker journey, so a slow appointment-api is detected and handled from written steps (workshop delivery 2: CP6-CP10) | done | gateway name [`292f4f2`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/292f4f2) · CP6 [`30be24a`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/30be24a) · CP3 rewritten [`59df160`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/59df160), [`42e86d2`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/42e86d2), [`2a8fa7d`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/2a8fa7d), [`d4f92ea`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/d4f92ea), [`65ebcce`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/65ebcce) · CP7 [`eb20096`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/eb20096), [`766a95f`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/766a95f) · CP8 [`321e41d`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/321e41d) · CP9 [`a5deb2c`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a5deb2c), [`c44a786`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/c44a786) · CP10: this file |
| OBS-REVIEW | As an operator, I want to know which logs, ids, health checks and metrics my three services produce today, so the observability work starts from the code | done (read only) | [`CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-adapters/src/main/java/co/edu/corhuila/barbersaas/appointment/adapter/in/http/CorrelationFilter.java) · [`JsonApi.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-adapters/src/main/java/co/edu/corhuila/barbersaas/appointment/adapter/out/http/JsonApi.java) · [`application.yml`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-app/src/main/resources/application.yml) |
| OBS-02 (team) | As an operator, I want rate, error and latency metrics on every HTTP service, so the team's SLOs can be measured | todo (my part: appointment, loyalty, finance-inventory) | Acceptance criteria in Daniel's [`observability-plan.md`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/observability-plan.md), section 5; the workshop's `StickerMetrics` is the prototype for loyalty-api |
| HU-NOTIF-001 | Receive appointment notifications | doing (unchanged) | My outbox side, week 10: appointment-api [#15](https://github.com/code-corhuila/barber-saas-appointment-api/pull/15), [#16](https://github.com/code-corhuila/barber-saas-appointment-api/pull/16) |

> **Where the work is.** In `code-corhuila` I have 0 commits, pull requests, reviews and issues
> between 2026-10-12 and 2026-10-18; my latest ones are the 2.0.0 pull requests of 2026-10-09
> (week 10). All the workshop work is in this fork, under `11-week/workshop/`, in 24 commits of
> 2026-10-19, and none of it is in `barber-saas-loyalty-api` yet.

## 2. My individual contribution
- **Final presentation, 2026-10-15**, with Daniel Cerquera, Carlos Leal and Carolay Arraut on the
  `v2.0.0` platform; appointment, loyalty and finance-inventory, my corte-2 domains, ran in the demo.
- **What my services already have** (read on `origin/develop`, nothing changed):
  `X-Correlation-Id` reused or created by `CorrelationFilter` and passed on by `JsonApi`;
  the request's id stored in each outbox row (`InternalOutboxHttpTest` checks `corr-book` and
  `corr-sticker`); ECS JSON logs; `/health`. Missing in all three: metrics, `traceparent`, SLOs,
  alerts and runbooks.
- **Workshop delivery 1 - observe the journey:**
  - [`01-journey.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/01-journey.md): the 10 hops from `barber-saas-api-gateway` to the `notification`
    collection, `traceparent` per HTTP hop and a `trace_parent` column in `loyalty.outbox_event`
    for the hop without a broker (ADR-016).
  - [`02-log-contract.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/02-log-contract.md): 20 fields, masking for tokens, `Idempotency-Key` and `clientId`, and
    one trace with a 3 s timeout and a `422` for a sticker the appointment already had.
  - [`03-instrumentation`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/03-instrumentation): Java 21 / Spring Boot service with a `traceparent` parser, an MVC
    interceptor, a streaming JSON log formatter and an appointment-api client with the limits of
    `JsonApi`; `StickerGrantTraceTest` passes ([`HOW-TO-RUN.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/03-instrumentation/HOW-TO-RUN.md)).
    I rewrote it on 2026-10-19 with my own design after comparing it with my teammates' code.
  - [`04-metrics.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/04-metrics.md): golden signals of 5 services, bounded labels, buckets around the 3 s timeout.
- **Workshop delivery 2 - run the journey:**
  - [`05-slo.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/05-slo.md): 27,300 grants per 30 days from KR1/KR6; SLO-LOY-1 99.5 %, SLO-LOY-2 95 % under
    0.5 s, SLO-LOY-3 99 % of `StickerGranted` within 60 s; budget of 136 failed grants, or 93.6
    minutes of opening hours.
  - [`06-alerts.yml`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/06-alerts.yml) and [`06-alert-policy.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/06-alert-policy.md): 12 recording rules and 6 burn-rate alerts with a
    low-traffic guard; `promtool check rules` and `promtool test rules` pass.
  - [`07-runbook.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/07-runbook.md): owners from `service-catalog.md` and the levers that exist (scale out,
    manual restart, rollback to `v2.0.0`, granting without `appointmentId`).
  - [`08-incident.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/08-incident.md): local drill (35 grants, 35 calls, no retry after a timeout) and a
    `promtool` replay at peak traffic: 90 failed grants (66 % of the budget), first page 7 minutes
    after the first failure, and 30 minutes of 2-second grants that fired no alert.

## 3. Blockers and risks
- **Nothing of the workshop runs in the platform.** `barber-saas-loyalty-api` exports no metrics,
  and `prometheus.yml` in `barber-saas-infra-postgres` loads no `rule_files` and has no Alertmanager.
- **The latency SLO misses short slowdowns.** The CP9 replay shows that 30 minutes of 2 s grants
  stay under every threshold; action item AI-1 of `08-incident.md` proposes a dependency alert.
- **A dependency timeout answers `500`** in `barber-saas-loyalty-api`, not `503` (AI-2).
- **No observability story in DOCS.** Every pull request needs its `barber-saas-docs#NN` (norm 9.1).
- **Still open from week 10:** the random tie-break in `InMemoryLoyaltyRepositoryTest`; the coupon
  branches `feat/coupon-at-booking`, `feat/appointment-coupon` and `feat/coupon-from-appointment-created`
  outside `develop`; my share of the 48 findings in [DOCS #111](https://github.com/code-corhuila/barber-saas-docs/issues/111).

## 4. Plan for next week
- Open the observability issue in `barber-saas-docs` with AI-1 to AI-4 of `08-incident.md` as its
  acceptance criteria, and agree the OBS-02 split with the team.
- Start with AI-2 in `barber-saas-loyalty-api` (test first, one small pull request to `develop`).
- Fix the flaky loyalty test on a `fix/` branch and promote it to `qa` with `git cherry-pick -x`.

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary`: 24 workshop commits with `docs`, `feat`,
      `refactor` and `test` on scope `w11`, none over 150 added lines.
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...): not used;
      the weekly fork takes commits on `main`, and no project repository changed this week.
- [x] Testable acceptance criteria: the CP9 action items are written in Given/When/Then, and the
      alert thresholds are proven by `06-alerts.test.yml` and `08-incident.test.yml`.
- [x] Tests added/updated (unit / integration): `StickerGrantTraceTest` (trace id downstream, the
      3 s timeout, `/metrics`) and two `promtool` rule test files.
- [x] DDD / hexagonal boundaries respected (domain has no I/O): tracing, logging and metrics stay
      in the HTTP layer of the workshop service; the team's `-core` modules are untouched.
- [x] No secrets; config via environment variables: `APPOINTMENT_API_URL`, `PORT`, `ENVIRONMENT`.

## 6. Evidence links
- Workshop folder: https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop
- Delivery 1: CP0 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/8918c9f · CP1 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f83bde4 · CP2 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/166d40f · CP3 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e57f2c7 · CP4 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/8a44d87 · CP5 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/141c1a6
- Delivery 2: CP6 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/30be24a · CP3 rewrite https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/59df160 · CP7 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/eb20096 · CP8 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/321e41d · CP9 https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a5deb2c and https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/c44a786
- Reflection document: https://docs.google.com/document/d/1B3urf8BFh60g9s0vP6rt7NgHMuKrxmlXutj1msSNJV8/edit?usp=sharing
- Release 2.0.0: https://github.com/code-corhuila/barber-saas-docs/issues/109 · https://github.com/code-corhuila/barber-saas-appointment-api/releases/tag/v2.0.0
- Team observability plan: https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/observability-plan.md
- **Record of the week:** `code-corhuila` 0 pull requests, 0 commits, 0 reviews, 0 issues
  (2026-10-12 to 2026-10-18). This fork: 24 workshop commits on 2026-10-19, listed in section 1.
