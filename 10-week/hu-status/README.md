<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       10-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 10

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Deliver my three domains (appointment, finance-inventory and loyalty) as running microservices — each with its -db, -api and -app — and connect them through the worker's events, so that completing an appointment grants a loyalty sticker and notifies the client end to end, by the week-10 checkpoint.
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| HU-APPT-001 | Book an appointment without double-booking | done | appointment-db [#2](https://github.com/code-corhuila/barber-saas-appointment-db/pull/2)–[#4](https://github.com/code-corhuila/barber-saas-appointment-db/pull/4) · appointment-api [#2](https://github.com/code-corhuila/barber-saas-appointment-api/pull/2)–[#12](https://github.com/code-corhuila/barber-saas-appointment-api/pull/12), [#14](https://github.com/code-corhuila/barber-saas-appointment-api/pull/14) · appointment-app [#2](https://github.com/code-corhuila/barber-saas-appointment-app/pull/2)–[#7](https://github.com/code-corhuila/barber-saas-appointment-app/pull/7), [#11](https://github.com/code-corhuila/barber-saas-appointment-app/pull/11) · [DOCS #80](https://github.com/code-corhuila/barber-saas-docs/pull/80) |
| HU-APPT-002 | Cancel an appointment within policy, and auto-mark no-shows | done | appointment-api [#10](https://github.com/code-corhuila/barber-saas-appointment-api/pull/10), [#16](https://github.com/code-corhuila/barber-saas-appointment-api/pull/16) (daily no-show job) · appointment-app [#12](https://github.com/code-corhuila/barber-saas-appointment-app/pull/12) (barber metrics) |
| HU-NOTIF-001 | Receive appointment notifications | doing | appointment-db [#6](https://github.com/code-corhuila/barber-saas-appointment-db/pull/6) · appointment-api [#15](https://github.com/code-corhuila/barber-saas-appointment-api/pull/15) (outbox relay), [#16](https://github.com/code-corhuila/barber-saas-appointment-api/pull/16) (reminders) — my side done; the notification itself is notifications-api (Carlos) |
| HU-LOY-001 | Earn and redeem loyalty rewards | done | loyalty-db [#3](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/3)–[#5](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/5) · loyalty-api [#3](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/3)–[#14](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/14) · loyalty-app [#3](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/3)–[#6](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/6) · appointment-api [#18](https://github.com/code-corhuila/barber-saas-appointment-api/pull/18) · [DOCS #91](https://github.com/code-corhuila/barber-saas-docs/pull/91) |
| HU-FIN-001 | Track barbershop income and expenses | done | finance-inventory-db [#3](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/3), [#4](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/4) · finance-inventory-api [#3](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/3)–[#12](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/12), [#14](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/14) · finance-inventory-app [#3](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/3)–[#5](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/5), [#7](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/7) |
| HU-INV-001 | Track product stock and low-stock alerts | done | finance-inventory-api [#8](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/8), [#13](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/13) · finance-inventory-app [#6](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/6) |
| HU-TENANT-001 | Tenant data isolation across every operation | doing | cross-barbershop tests in appointment-api [#10](https://github.com/code-corhuila/barber-saas-appointment-api/pull/10), finance-inventory-api [#12](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/12)/[#13](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/13), loyalty-api [#11](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/11) — done in my 3 services; the HU covers every service |
| GOV-AGILE-08-A | SPEC-008: WIP limit, story map, MVP2 backlog, sprint-ready stories | done | [DOCS #52](https://github.com/code-corhuila/barber-saas-docs/pull/52), [#54](https://github.com/code-corhuila/barber-saas-docs/pull/54), [#81](https://github.com/code-corhuila/barber-saas-docs/pull/81) (replaces #53) |
| GOV-INFRA-59 | Rename to barber-saas-infra-postgres and fix the "LMS Library" headers in my 9 repos | done | [DOCS issue #59](https://github.com/code-corhuila/barber-saas-docs/issues/59) · appointment-db [#5](https://github.com/code-corhuila/barber-saas-appointment-db/pull/5), appointment-api [#13](https://github.com/code-corhuila/barber-saas-appointment-api/pull/13) and the same PR in the other 7 repos |

## 2. My individual contribution
- **313 commits this week** (308 in the code repos, 5 in DOCS) and **90 merged pull requests**,
  all Conventional Commits with `Refs: code-corhuila/barber-saas-docs#NN`.
- **appointment** — `appointment-db` (Liquibase schema with the no-double-booking exclusion
  constraint, and later new changesets only: `failed_at`, `last_error`, `reminder_sent_at`, the
  new pending index), `appointment-api` (hexagonal Java 21 / Spring Boot 3.5: booking, state
  machine, cancellation window, busy slots for schedule, the outbox relay and the daily reminder
  and no-show jobs for the worker, `completedBy` in `AppointmentCompleted`), `appointment-app`
  (Ionic React: booking, my appointments across barbershops, agendas, history, barber metrics).
- **finance-inventory** — `-db`, `-api` (the 10 operations of the contract, stock moved by
  delta inside one transaction) and the Ionic Angular `-app` (dashboard, finance, inventory).
- **loyalty** — `-db` (with `processed_event`), `-api` (the 12 operations, the outbox relay and
  `POST /internal/v1/events` that grants one sticker per completed appointment even when the
  event arrives twice) and the Ionic Angular `-app` (client card and coupons, staff cards,
  stickers, redemptions, program).
- Shared pieces, only my lines: three route files in `barber-saas-api-gateway` and five
  `include` lines in `barber-saas-infra-postgres`.
- Contract proposals in DOCS that the reviewer approved: DOCS #80 (`DEC-APPT-06`, a client
  sees their appointments of every barbershop) and DOCS #91 (`DEC-APPT-08` / `DEC-LOY-05`, who
  grants the automatic sticker).
- **End-to-end check (2026-10-07, local):** with appointment-api, loyalty-api, the real
  `barber-saas-worker` and notifications-api — the barber completes an appointment → the worker
  relays `AppointmentCompleted` → loyalty grants 1 sticker in the barber's name → `StickerGranted`
  is published → the client sees "Ganaste un sello" and "Cita completada".

## 3. Blockers and risks
- I still get `404` on `barber-saas-infra-mongo`, so `./scripts/up.sh dev` and `migrate.sh`
  cannot run on my machine. I tested the migrations with Liquibase against a local PostgreSQL 16
  and the end to end with separate containers instead.
- The two Angular apps (`/finance`, `/loyalty`) are now registered in the shell, but I have not
  seen them inside the shell or the emulator yet.
- Three PRs broke the team rules and are already merged (not rewritten): loyalty-api #9 (637
  lines) and finance-inventory-app #3 (458 lines) exceed the 400-line limit (norm 9.2), and
  appointment-api #17 came from a `docs/` branch, which is not allowed into `develop`
  (norm 6.3.1).
- DOCS #88 is still open for smaller gaps: there is no contract operation to check that a client
  exists, no named service token for using a coupon at booking, and the source of each
  barbershop's time zone for the daily jobs (I used its public detail with `America/Bogota` as
  the fallback).
- To build the APK the build needs JDK 21 (Temurin); Android Studio now ships Java 25, which
  Gradle 8.11 does not support.

## 4. Plan for next week
- Check `/finance` and `/loyalty` inside the shell and on the Android emulator, and fix what
  shows up (3 SP).
- Run the end to end with Daniel on the full platform once I have access to
  `barber-saas-infra-mongo` (2 SP).
- Close DOCS #88 with the team: client existence, the booking flow's coupon token, the time
  zone source (2 SP).
- Promote the phase 2 work from `develop` to `qa` with `git cherry-pick -x`, one `qa/...` branch
  per story (3 SP).

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary` (every commit of the 90 PRs, e.g. `feat(http): grant the automatic sticker from AppointmentCompleted`)
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...) - every change went through a `feat/`, `fix/` or `chore/` branch and a PR to `develop`, but appointment-api #17 used a `docs/` branch; nothing was promoted to `qa` yet
- [x] Testable acceptance criteria - each PR lists its checks; annex C HTTP tests and cross-barbershop tests in the three APIs
- [x] Tests added/updated (unit / integration) - domain and use-case tests with fakes, HTTP tests over the whole service, JDBC tests against PostgreSQL (`TEST_DATABASE_URL`), Vitest in the apps; CI green on every merged PR
- [x] DDD / hexagonal boundaries respected (domain has no I/O) - `<domain>-core` has no Spring dependency, adapters and the composition root are separate Maven modules
- [x] No secrets; config via environment variables - `.env.example` with names only; database users and `JWT_PUBLIC_KEY` come from the environment

## 6. Evidence links
- https://github.com/code-corhuila/barber-saas-appointment-api/pulls?q=is%3Apr+is%3Amerged
- https://github.com/code-corhuila/barber-saas-loyalty-api/pulls?q=is%3Apr+is%3Amerged
- https://github.com/code-corhuila/barber-saas-finance-inventory-api/pulls?q=is%3Apr+is%3Amerged
- https://github.com/code-corhuila/barber-saas-loyalty-api/pull/14 (automatic sticker) · https://github.com/code-corhuila/barber-saas-appointment-api/pull/18 (`completedBy`)
- https://github.com/code-corhuila/barber-saas-docs/pull/91 · https://github.com/code-corhuila/barber-saas-docs/issues/88
- https://github.com/code-corhuila/barber-saas-api-gateway/pull/12 · https://github.com/code-corhuila/barber-saas-infra-postgres/pull/25
