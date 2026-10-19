<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       11-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 11

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: After the checkpoint-2 delivery (release `v2.0.0` of my 9 repositories, merged 2026-10-09), defend the release in the final presentation with the team and study the week's topic (observability: logs, metrics, traces, SLOs, alerts and runbooks) against my three services (`appointment-api`, `loyalty-api`, `finance-inventory-api`), so next week starts from a written list of what each one already has and what it is missing.
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| REL-2.0.0 | Release 2.0.0 (MVP 2) of my 9 repos, defended in the final presentation | done (merged 2026-10-09, reported in week 10; presented 2026-10-15) | PRs into `main`: appointment-api [#23](https://github.com/code-corhuila/barber-saas-appointment-api/pull/23), loyalty-api [#19](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/19), finance-inventory-api [#18](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/18) (and the `-db`/`-app` of each domain) · [DOCS #109](https://github.com/code-corhuila/barber-saas-docs/issues/109) · team deck: [`Sustentacion-BarberSaaS.pdf`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/Sustentacion-BarberSaaS.pdf) |
| OBS-REVIEW | As an operator, I want to know what logs, ids, health and metrics my three services already produce, so the observability work starts from facts (week 11, sessions 1 and 2) | done (review only, no code changed) | Section 2 table, read on `origin/develop`: [`CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-adapters/src/main/java/co/edu/corhuila/barbersaas/appointment/adapter/in/http/CorrelationFilter.java), [`JsonApi.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-adapters/src/main/java/co/edu/corhuila/barbersaas/appointment/adapter/out/http/JsonApi.java), [`application.yml`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-app/src/main/resources/application.yml) |
| OBS-02 (team) | As an operator, I want rate, errors and latency metrics of every HTTP service, so the team's SLOs can be measured | todo (my part: appointment, loyalty, finance-inventory) | Story and acceptance criteria in Daniel's [`observability-plan.md`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/observability-plan.md) §5; none of my three APIs exports metrics yet |
| HU-NOTIF-001 | Receive appointment notifications | doing (unchanged) | My side (outbox relay) done in week 10: appointment-api [#15](https://github.com/code-corhuila/barber-saas-appointment-api/pull/15), [#16](https://github.com/code-corhuila/barber-saas-appointment-api/pull/16). The worker's correlation gap (OBS-04) affects this flow |

> **No new code this week.** Between 2026-10-12 and 2026-10-18 I have no commits, pull requests,
> reviews or issues in `code-corhuila`: my last ones are the release 2.0.0 pull requests, merged
> on 2026-10-09 and reported in week 10. This week's work was the final presentation and the
> observability review of my services (section 2).

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
- [ ] Conventional Commits - `type(scope): summary`: no commits in the project repositories this
      week; the only commit is this delivery in the weekly repository (`docs(week-11): …`).
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...): no pull
      requests this week; the release 2.0.0 flow (`hu-mvp2-dev` → `develop` → … → `main`) was week 10.
- [x] Testable acceptance criteria: the review in section 2 is checkable in the code, and OBS-02
      has executable checks (a 500 increases that route's error series by 1).
- [ ] Tests added/updated (unit / integration): none this week.
- [x] DDD / hexagonal boundaries respected (domain has no I/O): unchanged; correlation and logging
      live in the HTTP adapters (`adapter/in/http`, `adapter/out/http`), not in `-core`.
- [x] No secrets; config via environment variables: unchanged; nothing was committed.

## 6. Evidence links
- Team deck of the final presentation (2026-10-15): https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/Sustentacion-BarberSaaS.pdf
- Team observability plan (SLOs, alerts, OBS-01…OBS-06, runbook RB-01): https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/11-week/hu-status/observability-plan.md
- Correlation id in my services: [appointment-api `CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-adapters/src/main/java/co/edu/corhuila/barbersaas/appointment/adapter/in/http/CorrelationFilter.java) · [loyalty-api `CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-loyalty-api/blob/develop/loyalty-adapters/src/main/java/co/edu/corhuila/barbersaas/loyalty/adapter/in/http/CorrelationFilter.java) · [finance-inventory-api `CorrelationFilter.java`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/blob/develop/finance-inventory-adapters/src/main/java/co/edu/corhuila/barbersaas/financeinventory/adapter/in/http/CorrelationFilter.java)
- Outbox keeps the request's id: [appointment-api `InternalOutboxHttpTest.java`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-app/src/test/java/co/edu/corhuila/barbersaas/appointment/app/InternalOutboxHttpTest.java) · [loyalty-api `InternalOutboxHttpTest.java`](https://github.com/code-corhuila/barber-saas-loyalty-api/blob/develop/loyalty-app/src/test/java/co/edu/corhuila/barbersaas/loyalty/app/InternalOutboxHttpTest.java)
- Release 2.0.0: https://github.com/code-corhuila/barber-saas-docs/issues/109 · https://github.com/code-corhuila/barber-saas-appointment-api/releases/tag/v2.0.0
- MVP 3 backlog: https://github.com/code-corhuila/barber-saas-docs/issues/111
- **Complete record of my GitHub work this week** (2026-10-12 to 2026-10-18): pull requests 0 ·
  commits 0 · reviews 0 · issues 0 in `code-corhuila`. Off-GitHub: the final presentation
  (2026-10-15) and the observability review in section 2.
