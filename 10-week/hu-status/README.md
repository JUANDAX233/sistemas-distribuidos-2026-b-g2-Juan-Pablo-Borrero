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
| GOV-INFRA-59 | Rename to barber-saas-infra-postgres and fix the "LMS Library" headers in my 9 repos | done | [DOCS issue #59](https://github.com/code-corhuila/barber-saas-docs/issues/59) · appointment-db [#5](https://github.com/code-corhuila/barber-saas-appointment-db/pull/5) · appointment-api [#13](https://github.com/code-corhuila/barber-saas-appointment-api/pull/13) · appointment-app [#8](https://github.com/code-corhuila/barber-saas-appointment-app/pull/8) · loyalty-db [#2](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/2) · loyalty-api [#2](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/2) · loyalty-app [#2](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/2) · finance-inventory-db [#2](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/2) · finance-inventory-api [#2](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/2) · finance-inventory-app [#2](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/2) |
| REL-2.0.0 | Release 2.0.0 (MVP 2): promote my 9 repos develop → qa → release.2.0.0 → main with `git cherry-pick -x` ([DOCS issue #109](https://github.com/code-corhuila/barber-saas-docs/issues/109)) | done | appointment-db [#8](https://github.com/code-corhuila/barber-saas-appointment-db/pull/8) → [#9](https://github.com/code-corhuila/barber-saas-appointment-db/pull/9) → [#10](https://github.com/code-corhuila/barber-saas-appointment-db/pull/10) → [#11](https://github.com/code-corhuila/barber-saas-appointment-db/pull/11) · appointment-api [#20](https://github.com/code-corhuila/barber-saas-appointment-api/pull/20) → [#21](https://github.com/code-corhuila/barber-saas-appointment-api/pull/21) → [#22](https://github.com/code-corhuila/barber-saas-appointment-api/pull/22) → [#23](https://github.com/code-corhuila/barber-saas-appointment-api/pull/23) · appointment-app [#13](https://github.com/code-corhuila/barber-saas-appointment-app/pull/13) → [#14](https://github.com/code-corhuila/barber-saas-appointment-app/pull/14) → [#15](https://github.com/code-corhuila/barber-saas-appointment-app/pull/15) → [#16](https://github.com/code-corhuila/barber-saas-appointment-app/pull/16) · loyalty-db [#6](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/6) → [#7](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/7) → [#8](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/8) → [#9](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/9) · loyalty-api [#16](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/16) → [#17](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/17) → [#18](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/18) → [#19](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/19) · loyalty-app [#7](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/7) → [#8](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/8) → [#9](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/9) → [#10](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/10) · finance-inventory-db [#5](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/5) → [#6](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/6) → [#7](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/7) → [#8](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/8) · finance-inventory-api [#15](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/15) → [#16](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/16) → [#17](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/17) → [#18](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/18) · finance-inventory-app [#8](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/8) → [#9](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/9) → [#10](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/10) → [#11](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/11) — PRs into `main` approved by the professor (`ariel5253`) and merged on 2026-10-08; tag `v2.0.0` and a GitHub Release in each repo |

## 2. My individual contribution
- **231 commits this week** (222 in my 9 code repos, 6 in the shared repos, 2 in DOCS, 1 in this
  repo; counted once, on `develop`/`main`, without the cherry-picked copies) and **106 pull
  requests** (104 opened this week, plus DOCS #52 and #54 from week 09 merged this week), every
  one listed in section 6. Conventional Commits with `Refs: code-corhuila/barber-saas-docs#NN`.
- **Release 2.0.0 — MVP 2, corte 2** ([DOCS issue #109](https://github.com/code-corhuila/barber-saas-docs/issues/109)):
  in each of my 9 repos, one PR to `develop` (CI only on PRs to `develop`/`qa`, 10-minute
  timeout, `CHANGELOG.md` with the 2.0.0 entry), one to `qa` re-applying every `develop` commit
  with `git cherry-pick -x`, one to `release.2.0.0` (cut from `main`) re-applying every `qa`
  commit, and one to `main` with stories, scope, deployment, rollback and the promotion trail
  (norm 11.3). 329 commits promoted per environment; `develop`, `qa` and `release.2.0.0` have the
  same tree, and every `(cherry picked from commit <sha>)` points to a commit that exists on its
  source branch. The professor approved the 9 PRs into `main`; they were merged on 2026-10-08
  and `v2.0.0` is tagged with a GitHub Release in each repo.
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
- I now have access to `barber-saas-infra-mongo` (checked 2026-10-09), but `./scripts/up.sh dev`
  still stops on my machine because my local `env/dev.env` has empty passwords. Until now I
  tested the migrations with Liquibase against a local PostgreSQL 16 and the end to end with
  separate containers.
- `loyalty-api` has a flaky test (`InMemoryLoyaltyRepositoryTest`, 2 failures in 5 local runs):
  the in-memory adapter orders transactions by `createdAt` and breaks ties by a random UUID.
  CI passed on every PR, but it can fail at random until it is fixed.
- The release branches follow the professor's flow in DOCS #109 (`hu-mvp2-*`,
  `release.2.0.0`), not the names of norm 6.3 (`qa/…`, `release/2.0.0`) still written in
  `00-governance/git-conventions.md`; the team has to record that difference.
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
- Run the end to end with Daniel on the full platform with `barber-saas-infra-postgres` and
  `barber-saas-infra-mongo` (2 SP).
- Close DOCS #88 with the team: client existence, the booking flow's coupon token, the time
  zone source (2 SP).
- Fix the flaky ordering test in `loyalty-api` with a `fix/` branch and promote it with
  `cherry-pick -x` (1 SP).
- Record the release branch names of DOCS #109 against norm 6.3 in the team's conventions (1 SP).

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary` (e.g. `feat(http): grant the automatic sticker from AppointmentCompleted`); one exception: appointment-db `59a0120` uses `revert:`, a type outside norm 8.2, already published and not rewritten
- [x] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...) - release 2.0.0 in my 9 repos: `hu-mvp2-dev` → `develop`, `hu-mvp2-qa` → `qa`, `hu-mvp2-release` → `release.2.0.0`, `release.2.0.0` → `main` (36 PRs, see REL-2.0.0); the feature work went through `feat/`, `fix/` and `chore/` branches into `develop`, except appointment-api #17 (`docs/` branch)
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
- https://github.com/code-corhuila/barber-saas-docs/issues/109 (release 2.0.0) · https://github.com/code-corhuila/barber-saas-appointment-api/releases/tag/v2.0.0

### Every pull request I opened or merged this week (106)

| Repository | PR | Title | Into | State |
|---|---|---|---|---|
| barber-saas-api-gateway | [#9](https://github.com/code-corhuila/barber-saas-api-gateway/pull/9) | feat(routes): route the finance-inventory domain | `develop` | merged 2026-10-06 |
| barber-saas-api-gateway | [#12](https://github.com/code-corhuila/barber-saas-api-gateway/pull/12) | feat(routes): route the loyalty domain | `develop` | merged 2026-10-07 |
| barber-saas-appointment-api | [#12](https://github.com/code-corhuila/barber-saas-appointment-api/pull/12) | feat(http): answer the busy slots of a barber to schedule-api | `develop` | merged 2026-10-05 |
| barber-saas-appointment-api | [#13](https://github.com/code-corhuila/barber-saas-appointment-api/pull/13) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-appointment-api | [#14](https://github.com/code-corhuila/barber-saas-appointment-api/pull/14) | feat(appointment): let a client without a barbershop list their appointments | `develop` | merged 2026-10-06 |
| barber-saas-appointment-api | [#15](https://github.com/code-corhuila/barber-saas-appointment-api/pull/15) | feat(http): relay the outbox to barber-saas-worker | `develop` | merged 2026-10-06 |
| barber-saas-appointment-api | [#16](https://github.com/code-corhuila/barber-saas-appointment-api/pull/16) | feat(http): run the daily reminder and no-show jobs for the worker | `develop` | merged 2026-10-07 |
| barber-saas-appointment-api | [#17](https://github.com/code-corhuila/barber-saas-appointment-api/pull/17) | docs(readme): describe the worker's internal operations | `develop` | merged 2026-10-07 |
| barber-saas-appointment-api | [#18](https://github.com/code-corhuila/barber-saas-appointment-api/pull/18) | feat(appointment): add completedBy to AppointmentCompleted | `develop` | merged 2026-10-07 |
| barber-saas-appointment-api | [#20](https://github.com/code-corhuila/barber-saas-appointment-api/pull/20) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-appointment-api | [#21](https://github.com/code-corhuila/barber-saas-appointment-api/pull/21) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-appointment-api | [#22](https://github.com/code-corhuila/barber-saas-appointment-api/pull/22) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-appointment-api | [#23](https://github.com/code-corhuila/barber-saas-appointment-api/pull/23) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
| barber-saas-appointment-app | [#7](https://github.com/code-corhuila/barber-saas-appointment-app/pull/7) | feat(ui): book with the token bound to the barbershop and show barber names | `develop` | merged 2026-10-05 |
| barber-saas-appointment-app | [#8](https://github.com/code-corhuila/barber-saas-appointment-app/pull/8) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-appointment-app | [#9](https://github.com/code-corhuila/barber-saas-appointment-app/pull/9) | fix(ui): margin between the agenda's day chips and the first card | `develop` | merged 2026-10-06 |
| barber-saas-appointment-app | [#10](https://github.com/code-corhuila/barber-saas-appointment-app/pull/10) | fix(ui): no empty space at the bottom of a completed appointment card | `develop` | merged 2026-10-06 |
| barber-saas-appointment-app | [#11](https://github.com/code-corhuila/barber-saas-appointment-app/pull/11) | feat(appointment): name each client appointment's barbershop, barber and service | `develop` | merged 2026-10-06 |
| barber-saas-appointment-app | [#12](https://github.com/code-corhuila/barber-saas-appointment-app/pull/12) | feat(ui): show the barber's metrics of a day, week or month | `develop` | merged 2026-10-07 |
| barber-saas-appointment-app | [#13](https://github.com/code-corhuila/barber-saas-appointment-app/pull/13) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-appointment-app | [#14](https://github.com/code-corhuila/barber-saas-appointment-app/pull/14) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-appointment-app | [#15](https://github.com/code-corhuila/barber-saas-appointment-app/pull/15) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-appointment-app | [#16](https://github.com/code-corhuila/barber-saas-appointment-app/pull/16) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
| barber-saas-appointment-db | [#5](https://github.com/code-corhuila/barber-saas-appointment-db/pull/5) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-appointment-db | [#6](https://github.com/code-corhuila/barber-saas-appointment-db/pull/6) | feat(ddl): outbox failure columns, reminder mark and the new pending index | `develop` | merged 2026-10-06 |
| barber-saas-appointment-db | [#8](https://github.com/code-corhuila/barber-saas-appointment-db/pull/8) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-appointment-db | [#9](https://github.com/code-corhuila/barber-saas-appointment-db/pull/9) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-appointment-db | [#10](https://github.com/code-corhuila/barber-saas-appointment-db/pull/10) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-appointment-db | [#11](https://github.com/code-corhuila/barber-saas-appointment-db/pull/11) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
| barber-saas-docs | [#52](https://github.com/code-corhuila/barber-saas-docs/pull/52) | docs(agile-process): define the wip limit and track sprint status | `main` | merged 2026-10-06 (opened in week 09) |
| barber-saas-docs | [#54](https://github.com/code-corhuila/barber-saas-docs/pull/54) | docs(product): add the story map and the mvp2 backlog | `main` | merged 2026-10-06 (opened in week 09) |
| barber-saas-docs | [#80](https://github.com/code-corhuila/barber-saas-docs/pull/80) | docs(api): let a client without a barbershop list their appointments | `main` | merged 2026-10-06 |
| barber-saas-docs | [#81](https://github.com/code-corhuila/barber-saas-docs/pull/81) | docs(requirements): split the backlog into sprint-ready user stories | `main` | merged 2026-10-06 |
| barber-saas-docs | [#91](https://github.com/code-corhuila/barber-saas-docs/pull/91) | docs(api): who grants the automatic sticker, and no couponCode | `main` | merged 2026-10-07 |
| barber-saas-finance-inventory-api | [#2](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/2) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#3](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/3) | chore(build): add the maven build and the ci pipeline | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#4](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/4) | feat(domain): add the finance record and the inventory product | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#5](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/5) | feat(application): declare the ports of the finance-inventory service | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#6](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/6) | feat(application): add the finance and inventory use cases | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#7](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/7) | feat(persistence): add the jdbc and in-memory finance repositories | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#8](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/8) | feat(persistence): add the jdbc and in-memory inventory repositories | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#9](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/9) | feat(http): add the client of appointment-api | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#10](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/10) | feat(http): add the error envelope, correlation, health and token validation | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#11](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/11) | feat(app): compose the service with its adapters and limits | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#12](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/12) | feat(http): expose the finance endpoints | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#13](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/13) | feat(http): expose the inventory endpoints | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#14](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/14) | feat(deploy): compose the finance-inventory service and document it | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-api | [#15](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/15) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-finance-inventory-api | [#16](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/16) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-finance-inventory-api | [#17](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/17) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-finance-inventory-api | [#18](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/18) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
| barber-saas-finance-inventory-app | [#2](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/2) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-app | [#3](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/3) | chore(build): scaffold the Ionic Angular domain app | `develop` | merged 2026-10-07 |
| barber-saas-finance-inventory-app | [#4](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/4) | feat(finance): add the calls, types and form rules | `develop` | merged 2026-10-07 |
| barber-saas-finance-inventory-app | [#5](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/5) | feat(ui): show the month's finances and record income and expenses | `develop` | merged 2026-10-07 |
| barber-saas-finance-inventory-app | [#6](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/6) | feat(ui): manage the products and their stock movements | `develop` | merged 2026-10-07 |
| barber-saas-finance-inventory-app | [#7](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/7) | feat(ui): add the owner's dashboard and document the app | `develop` | merged 2026-10-07 |
| barber-saas-finance-inventory-app | [#8](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/8) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-finance-inventory-app | [#9](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/9) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-finance-inventory-app | [#10](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/10) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-finance-inventory-app | [#11](https://github.com/code-corhuila/barber-saas-finance-inventory-app/pull/11) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
| barber-saas-finance-inventory-db | [#2](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/2) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-db | [#3](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/3) | chore(liquibase): scaffold the finance_inventory migrations | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-db | [#4](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/4) | feat(ddl): create the finance_inventory schema | `develop` | merged 2026-10-06 |
| barber-saas-finance-inventory-db | [#5](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/5) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-finance-inventory-db | [#6](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/6) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-finance-inventory-db | [#7](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/7) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-finance-inventory-db | [#8](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/8) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
| barber-saas-infra-postgres | [#17](https://github.com/code-corhuila/barber-saas-infra-postgres/pull/17) | feat(compose): include finance-inventory-db in the platform | `develop` | merged 2026-10-06 |
| barber-saas-infra-postgres | [#18](https://github.com/code-corhuila/barber-saas-infra-postgres/pull/18) | feat(compose): include finance-inventory-api in the platform | `develop` | merged 2026-10-06 |
| barber-saas-infra-postgres | [#24](https://github.com/code-corhuila/barber-saas-infra-postgres/pull/24) | feat(compose): include loyalty-db in the platform | `develop` | merged 2026-10-07 |
| barber-saas-infra-postgres | [#25](https://github.com/code-corhuila/barber-saas-infra-postgres/pull/25) | feat(compose): include loyalty-api in the platform | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#2](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/2) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-loyalty-api | [#3](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/3) | chore(build): add the maven build and the ci pipeline | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#4](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/4) | feat(domain): add the loyalty card, its rule and the coupon | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#5](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/5) | feat(application): declare the ports of the loyalty service | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#6](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/6) | feat(application): add the loyalty use cases | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#7](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/7) | feat(persistence): add the jdbc loyalty repository | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#8](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/8) | feat(persistence): add the in-memory loyalty repository | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#9](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/9) | feat(http): add the error envelope, token validation and the appointment-api client | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#10](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/10) | feat(app): compose the service with its adapters and limits | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#11](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/11) | feat(http): expose the loyalty endpoints | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#12](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/12) | feat(http): relay loyalty's outbox to barber-saas-worker | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#13](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/13) | feat(deploy): compose the loyalty service and document it | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#14](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/14) | feat(http): grant the automatic sticker from AppointmentCompleted | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-api | [#16](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/16) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-loyalty-api | [#17](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/17) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-loyalty-api | [#18](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/18) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-loyalty-api | [#19](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/19) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
| barber-saas-loyalty-app | [#2](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/2) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-loyalty-app | [#3](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/3) | chore(build): add the Angular remote build and its CI | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-app | [#4](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/4) | feat(loyalty): add the shell contract, calls, types and rules | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-app | [#5](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/5) | feat(ui): show a client's loyalty card and coupons | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-app | [#6](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/6) | feat(ui): manage the cards and the program as staff | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-app | [#7](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/7) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-loyalty-app | [#8](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/8) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-loyalty-app | [#9](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/9) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-loyalty-app | [#10](https://github.com/code-corhuila/barber-saas-loyalty-app/pull/10) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
| barber-saas-loyalty-db | [#2](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/2) | chore: Barber Saas header and barber-saas-infra-postgres name | `develop` | merged 2026-10-06 |
| barber-saas-loyalty-db | [#3](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/3) | chore(liquibase): scaffold the loyalty migrations | `develop` | merged 2026-10-06 |
| barber-saas-loyalty-db | [#4](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/4) | feat(ddl): create the loyalty schema | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-db | [#5](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/5) | fix(ddl): align processed_event with the data model | `develop` | merged 2026-10-07 |
| barber-saas-loyalty-db | [#6](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/6) | chore(release): prepare 2.0.0 - CI rules and CHANGELOG | `develop` | merged 2026-10-08 |
| barber-saas-loyalty-db | [#7](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/7) | chore(release): promote MVP 2 to qa with cherry-pick -x | `qa` | merged 2026-10-08 |
| barber-saas-loyalty-db | [#8](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/8) | chore(release): fill release 2.0.0 from qa with cherry-pick -x | `release.2.0.0` | merged 2026-10-08 |
| barber-saas-loyalty-db | [#9](https://github.com/code-corhuila/barber-saas-loyalty-db/pull/9) | release: 2.0.0 - MVP 2 (corte 2) | `main` | merged 2026-10-08 |
