<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       11-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 11

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: After the checkpoint-2 delivery (release `v2.0.0` of my 9 repositories, merged 2026-10-09), defend the release in the final presentation with the team and study the week's topic (observability: logs, metrics, traces, SLOs, alerts and runbooks) against my three services (`appointment-api`, `loyalty-api`, `finance-inventory-api`), so next week starts from a written list of what each one already has and what it is missing; then deliver part 1 of the individual observability workshop (CP0-CP5) on the loyalty sticker journey (HU-LOY-001).
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| REL-2.0.0 | Release 2.0.0 (MVP 2) of my 9 repos, defended in the final presentation | done (merged 2026-10-09, reported in week 10; presented 2026-10-15) | PRs into `main`: appointment-api [#23](https://github.com/code-corhuila/barber-saas-appointment-api/pull/23), loyalty-api [#19](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/19), finance-inventory-api [#18](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/18) (and the `-db`/`-app` of each domain) · [DOCS #109](https://github.com/code-corhuila/barber-saas-docs/issues/109) · team deck: [`Sustentacion-BarberSaaS.pdf`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/Sustentacion-BarberSaaS.pdf) |
| OBS-REVIEW | As an operator, I want to know what logs, ids, health and metrics my three services already produce, so the observability work starts from facts (week 11, sessions 1 and 2) | done (review only, no code changed) | Section 2 table, read on `origin/develop`: [`CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-adapters/src/main/java/co/edu/corhuila/barbersaas/appointment/adapter/in/http/CorrelationFilter.java), [`JsonApi.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-adapters/src/main/java/co/edu/corhuila/barbersaas/appointment/adapter/out/http/JsonApi.java), [`application.yml`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-app/src/main/resources/application.yml) |
| OBS-02 (team) | As an operator, I want rate, errors and latency metrics of every HTTP service, so the team's SLOs can be measured | todo (my part: appointment, loyalty, finance-inventory) | Story and acceptance criteria in Daniel's [`observability-plan.md`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/observability-plan.md) §5; none of my three APIs exports metrics yet |
| W11-WS-1 | As an operator, I want the sticker journey (HU-LOY-001: `POST /api/v1/loyalty/stickers` → appointment-api → `StickerGranted` → worker → notifications-api) to be traceable, logged and measured end to end, so a slow appointment-api can be found from one trace id (workshop delivery 1, CP0-CP4) | done | CP0 [`8918c9f`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/8918c9f) · CP1 [`f83bde4`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f83bde4) · CP2 [`166d40f`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/166d40f) · CP3 [`e57f2c7`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e57f2c7), [`d09ff8b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/d09ff8b), [`34cd921`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/34cd921), [`ff47acb`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/ff47acb), [`1a15fd5`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/1a15fd5) · CP4 [`8a44d87`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/8a44d87) |
| HU-NOTIF-001 | Receive appointment notifications | doing (unchanged) | My side (outbox relay) done in week 10: appointment-api [#15](https://github.com/code-corhuila/barber-saas-appointment-api/pull/15), [#16](https://github.com/code-corhuila/barber-saas-appointment-api/pull/16). The worker's correlation gap (OBS-04) affects this flow |

> **No new code in the project repositories.** Between 2026-10-12 and 2026-10-18 I have no commits,
> pull requests, reviews or issues in `code-corhuila`: my last ones are the release 2.0.0 pull
> requests, merged on 2026-10-09 and reported in week 10. This week's work was the final
> presentation, the observability review of my services (section 2) and the workshop delivery 1,
> whose code lives in this fork (`11-week/workshop/`), not in `barber-saas-loyalty-api`.

## 2. My individual contribution
- **Final presentation (2026-10-15)** with Daniel Cerquera, Carlos Leal and Carolay, on the
  release `v2.0.0` stack (problem → architecture → service orchestration → front in Docker → live
  demos), using the team deck linked in section 6. The three domains I delivered in corte 2
  (appointment, loyalty, finance-inventory) are part of the platform that was shown running.
- **Observability session (sessions 1 and 2):** monitoring vs observability, the three pillars
  (logs, metrics, traces), one trace id across services, structured JSON logs, golden signals,
  SLI / SLO / error budget, alerts with owner and runbook.
- **Review of my three services against the session**, read on `origin/develop` (no code changed):

  | Session topic | `appointment-api` | `loyalty-api` | `finance-inventory-api` |
  |---|---|---|---|
  | One id across services (sync) | yes: `CorrelationFilter` reuses or creates `X-Correlation-Id`, puts it in the MDC and in `ApiError.traceId`; `JsonApi` forwards it to loyalty-api / schedule-api | yes: same filter; `JsonApi` forwards it to appointment-api | yes: same filter; `JsonApi` forwards it to appointment-api |
  | One id across the outbox (async) | yes on my side: each outbox row stores the `correlationId` of the request and `GET /internal/v1/outbox` returns it (`InternalOutboxHttpTest` checks `corr-book`) | yes: `StickerGranted` keeps it (`InternalOutboxHttpTest` checks `corr-sticker`) | no outbox |
  | Structured logs (JSON) | yes: `logging.structured.format.console: ecs`, `correlationId` from the MDC | yes, same | yes, same |
  | Health | yes: `HealthController` | yes | yes |
  | Metrics (latency, traffic, errors, saturation) | **no**: no Actuator / Micrometer / OTLP | **no** | **no** |
  | SLI / SLO, alerts, runbook | **no** | **no** | **no** |

  The sync id and the JSON logs already let a request be followed from the gateway into my
  services and from appointment-api into loyalty-api. Two things break that chain: the worker
  sends its own run id instead of the event's `correlationId` when it delivers
  `AppointmentCompleted` to loyalty-api (Daniel's OBS-04), so a completed appointment and the
  sticker it grants do not share one id in the logs; and without metrics there is nothing to
  measure the booking SLOs (SLO-1, SLO-2 of the team plan) against or to alert on.

- **Observability workshop, delivery 1 (2026-10-19)** in [`11-week/workshop/`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop), on the
  sticker journey of `loyalty-api` (my domain), with `appointment-api` as the slow synchronous
  dependency for CP9:
  - CP0 [`README.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/README.md): config block and scope.
  - CP1 [`01-journey.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/01-journey.md): 10 hops, `traceparent` on every HTTP hop and a
    `trace_parent` column in `loyalty.outbox_event` for the asynchronous hop (no broker, ADR-016),
    and the span tree with the worker span keeping the same `trace_id`.
  - CP2 [`02-log-contract.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/02-log-contract.md): 11 required and 9 domain fields, a
    never-log list (JWT, worker token, `Idempotency-Key`, `clientId`), 7 JSON lines of one trace
    with a 3 s timeout to appointment-api and the `422` of a duplicate sticker.
  - CP3 [`03-instrumentation/`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/03-instrumentation): Java 21 / Spring Boot 3.5.6 filter that
    continues or starts the W3C trace, a structured log formatter, a child `traceparent` to
    `GET /api/v1/appointments/{id}`, `/metrics` with `loyalty_sticker_requests_total{outcome}` and
    `loyalty_sticker_request_duration_seconds`, and `TracePropagationTest` (passing; real output
    in [`HOW-TO-RUN.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/03-instrumentation/HOW-TO-RUN.md)).
  - CP4 [`04-metrics.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/04-metrics.md): golden signals of the 5 services, label
    cardinality, forbidden labels and the 0.05-5 s buckets around the 3 s timeout.

## 3. Blockers and risks
- **No delivery this week.** The week after the checkpoint went to the presentation and the class
  topic; no story moved in my repositories.
- **Observability has no story in DOCS yet.** Under norm 9.1 every pull request needs its
  `barber-saas-docs#NN`, so OBS-02 for my three APIs cannot start until the issue exists.
- **Flaky test still open in `loyalty-api`:** `InMemoryLoyaltyRepository` sorts by `createdAt` and
  breaks ties with the random `id`, so `InMemoryLoyaltyRepositoryTest` can still fail at random
  (reported in week 10, not fixed yet).
- **Work left on feature branches after the release:** `appointment-api` `feat/coupon-at-booking`,
  `appointment-db` `feat/appointment-coupon` and `loyalty-api` `feat/coupon-from-appointment-created`
  (last commits 2026-10-08) are not in `develop`; they belong to MVP 3 and must not be lost.
- **The workshop code is not in the team's service yet.** `barber-saas-loyalty-api` still has no
  `traceparent`, no `/metrics` and no `trace_parent` column; moving them there needs a
  `barber-saas-docs#NN` story and a new changeset in `loyalty-db`.
- **48 review findings of release 2.0.0 are the MVP 3 backlog**
  ([DOCS #111](https://github.com/code-corhuila/barber-saas-docs/issues/111)); the ones in my 9
  repositories still have to be picked up.

## 4. Plan for next week
- Agree with the team on the observability issue in DOCS and the split of OBS-02, then add the
  latency and error metrics to `appointment-api` first (booking is SLO-2's path), test first, in a
  small pull request to `develop`.
- Fix the flaky ordering test in `loyalty-api` with a `fix/` branch and promote it to `qa` with
  `git cherry-pick -x`.
- Read DOCS #111 and list the findings that belong to my 9 repositories, with one story each.
- Decide with the team which of the three coupon feature branches go into MVP 3, and open their
  pull requests to `develop` with their `barber-saas-docs#NN`.

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary`: the workshop commits use `docs(w11)`,
      `feat(w11)` and `test(w11)`, each under 150 added lines; none in the project repositories.
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...): no pull
      requests this week; the release 2.0.0 flow (`hu-mvp2-dev` → `develop` → … → `main`) was week 10.
- [x] Testable acceptance criteria: the review in section 2 is checkable in the code, and OBS-02
      has executable checks (a 500 increases that route's error series by 1).
- [x] Tests added/updated (unit / integration): `TracePropagationTest` (workshop CP3) checks that
      the trace id received is the one sent to appointment-api and that `/metrics` exposes it.
- [x] DDD / hexagonal boundaries respected (domain has no I/O): unchanged; correlation and logging
      live in the HTTP adapters (`adapter/in/http`, `adapter/out/http`), not in `-core`.
- [x] No secrets; config via environment variables: unchanged; nothing was committed.

## 6. Evidence links
- Team deck of the final presentation (2026-10-15): https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/Sustentacion-BarberSaaS.pdf
- Team observability plan (SLOs, alerts, OBS-01…OBS-06, runbook RB-01): https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/observability-plan.md
- Correlation id in my services: [appointment-api `CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-adapters/src/main/java/co/edu/corhuila/barbersaas/appointment/adapter/in/http/CorrelationFilter.java) · [loyalty-api `CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-loyalty-api/blob/develop/loyalty-adapters/src/main/java/co/edu/corhuila/barbersaas/loyalty/adapter/in/http/CorrelationFilter.java) · [finance-inventory-api `CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/blob/develop/finance-inventory-adapters/src/main/java/co/edu/corhuila/barbersaas/financeinventory/adapter/in/http/CorrelationFilter.java)
- Outbox keeps the request's id: [appointment-api `InternalOutboxHttpTest.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-app/src/test/java/co/edu/corhuila/barbersaas/appointment/app/InternalOutboxHttpTest.java) · [loyalty-api `InternalOutboxHttpTest.java`](https://github.com/code-corhuila/barber-saas-loyalty-api/blob/develop/loyalty-app/src/test/java/co/edu/corhuila/barbersaas/loyalty/app/InternalOutboxHttpTest.java)
- Observability workshop, delivery 1: https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop
- Release 2.0.0: https://github.com/code-corhuila/barber-saas-docs/issues/109 · https://github.com/code-corhuila/barber-saas-appointment-api/releases/tag/v2.0.0
- MVP 3 backlog: https://github.com/code-corhuila/barber-saas-docs/issues/111
- **Complete record of my GitHub work this week** (2026-10-12 to 2026-10-18): pull requests 0 ·
  commits 0 · reviews 0 · issues 0 in `code-corhuila`. Off-GitHub: the final presentation
  (2026-10-15) and the observability review in section 2. In this fork: the 9 workshop commits
  listed in section 1 (2026-10-19).
