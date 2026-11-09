<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       14-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 14

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Move my MVP 3 work (appointment and finance-inventory) from `develop` to `qa` with `git cherry-pick -x`, so the 3.0.0 release candidate that the team deploys carries reviews, the one-hour reminder, the suspended-barbershop rule and the automatic income; and keep the Huawei HCCDP-Solution Architectures course moving (chapters 7 to 10).
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| MVP3-QA (appointment, finance-inventory) | As the team, we want the MVP 3 commits of appointment and finance-inventory on `qa`, with the trail of each one, so the release candidate is the same code that was reviewed on `develop` | done - 5 PRs merged 2026-11-05 | [appointment-db #13](https://github.com/code-corhuila/barber-saas-appointment-db/pull/13) (5 commits) · [appointment-api #30](https://github.com/code-corhuila/barber-saas-appointment-api/pull/30) (14) · [appointment-app #20](https://github.com/code-corhuila/barber-saas-appointment-app/pull/20) (7) · [finance-inventory-db #10](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/10) (1) · [finance-inventory-api #20](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/20) (1). 28 commits in all, every one with its `cherry picked from` line; in the 5 repos `git diff origin/develop hu-mvp3-qa` was empty before merging |
| J-1 (from week 12) | As the team, we want the `review` table and the one-hour reminder mark in the appointment database | done - on `qa` 2026-11-05 | [`dc885ad`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/dc885ad) create review · [`40c909b`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/40c909b) one review per appointment · [`a1b9ef6`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/a1b9ef6) `hour_reminder_sent_at` · via [appointment-db #13](https://github.com/code-corhuila/barber-saas-appointment-db/pull/13) |
| J-2 | As a client, I want a clear message when the barbershop is suspended, instead of a booking that can never happen | done - on `qa` 2026-11-05 | [`cf2b371`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/cf2b371) · [`efc987f`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/efc987f) · via [appointment-api #30](https://github.com/code-corhuila/barber-saas-appointment-api/pull/30) |
| J-3 / J-5 | As a client, I want to rate a finished appointment once, and as an owner I want to read the reviews by barber | done - on `qa` 2026-11-05 | API: [`bd5caac`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/bd5caac), [`633a56b`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/633a56b) · app: [`107aee8`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/107aee8), [`48ccda8`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/48ccda8) · via [#30](https://github.com/code-corhuila/barber-saas-appointment-api/pull/30) and [appointment-app #20](https://github.com/code-corhuila/barber-saas-appointment-app/pull/20) |
| J-4 | As a client, I want one reminder an hour before my appointment, never two | done - on `qa` 2026-11-05 | [`47e2b9f`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/47e2b9f) · via [appointment-api #30](https://github.com/code-corhuila/barber-saas-appointment-api/pull/30) |
| CHANGELOG-3.0.0 (my repos) | As the team, we want a `[3.0.0]` section in the `CHANGELOG.md` of every repo that changed since 2.0.0 | todo | Checked on 2026-11-08: the `develop` changelog of appointment-db/api/app and finance-inventory-db/api has no `3.0.0` entry yet. It has to be written and promoted before `qa` → `main` |
| W13-TM (week 13 workshop) | As the team, we want the TB4 threat model (worker → `/internal/v1`) merged in DOCS | doing - PR still open | [DOCS #154](https://github.com/code-corhuila/barber-saas-docs/pull/154) open, reviewed 2026-10-29/30, no new activity this week · stories [#158](https://github.com/code-corhuila/barber-saas-docs/issues/158), [#159](https://github.com/code-corhuila/barber-saas-docs/issues/159), [#160](https://github.com/code-corhuila/barber-saas-docs/issues/160) open |
| DEP-03 (my part) | As the operator, I want a readiness check in loyalty, appointment and finance-inventory | todo (unchanged) | Not started this week |
| HCCDP-C3 | As a student, I want to complete the Huawei HCCDP-Solution Architectures course and keep its evidence in this repo | doing - chapters 7 to 10 this week, 71 % of the course | [`e449980`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e449980) ch. 7 · [`f4d520b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f4d520b) ch. 8 · [`a166097`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a166097) ch. 9 · [`e6d41b7`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e6d41b7) ch. 10 · log in [`certification/README.md`](../../certification/README.md) |

## 2. My individual contribution
- **Promotion of MVP 3 to `qa` (2026-11-05).** For each of my five repos I opened a `hu-mvp3-qa`
  branch from `qa` and re-applied, in order, the commits that `develop` had and `qa` did not, all
  with `git cherry-pick -x`. None had conflicts. Before opening the PRs I compared the branch with
  `origin/develop` and the diff was empty in all five, so `qa` received exactly the reviewed code.
  Each PR body has a trail table (sha on the branch → sha on `develop`) and every cited sha was checked
  to be an ancestor of `origin/develop`. I merged them with "Rebase and merge" so `qa` keeps one commit
  per change.
- **What I ran before merging.** Actions is still locked by billing, so the checks were local:
  appointment-api `mvn -B verify` with 228 tests and 0 failures, finance-inventory-api 74 tests and 0
  failures (both with the JDBC tests against a throwaway PostgreSQL 16.4 migrated from the `-db`
  repo); appointment-app `npm test` (39 tests), `npx tsc --noEmit` and `npm run build`; and for the two
  `-db` repos Liquibase 4.29 update, a second update that runs 0 changesets, full rollback and update
  again. I also confirmed that no applied `.sql` file changed against `qa`, only new files.
- **Teammates' commits inside my repos.** 5 of the 28 commits were written by Daniel Cerquera on
  `develop`: the barbershop name in the reminder (`d9a8fe1`), the reviewed flag (`7e105c9`),
  "Calificar" shown only once (`ed393ed`), and the finance `processed_event` table and automatic
  income (`271009f`, `a8aa7cf`). I promoted them with the rest and he stays as their author.
- **Huawei course.** Chapters 7 (Cloud Security), 8 (Cloud O&M), 9 (Distributed Architecture) and
  10 (Highly Scalable System) with a screenshot of each one; 13 hours this week, 29 in total.

## 3. Blockers and risks
- **No `[3.0.0]` changelog in my five repos.** The release to `main` cannot use them until that
  section exists on `develop` and `qa`.
- **GitHub Actions still locked by billing.** No CI ran on the five PRs; the evidence is the local
  output written in each PR body. A red build would not have stopped the merge.
- **DOCS #154 still open** since 2026-10-30, so the TB4 threat model is not on `main` of DOCS and
  stories #158 to #160 (worker scopes for my internal endpoints) are not started.
- I did not run the whole platform end to end myself this week; the joint test of `develop` before
  the promotion was run by Daniel Cerquera, not by me.

## 4. Plan for next week
- Write the `[3.0.0]` section of `CHANGELOG.md` in appointment-db/api/app and
  finance-inventory-db/api, from `git log` since 2.0.0, and promote it to `qa` with `cherry-pick -x`.
- Ask for the merge of DOCS #154 and pick one of #158 to #160 for appointment-api or loyalty-api.
- Week 15 (testing at scale): run the booking and review journey against the deployed host.
- Huawei course: chapters 11 and 12, then labs and the final exam.

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary` — PR titles `chore(release): promote MVP 3 to qa with cherry-pick -x`; every promoted commit kept its original subject.
- [x] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...) — `hu-mvp3-qa` → PR → `qa` in the 5 repos; nothing pushed straight to `qa` or `main`.
- [x] Testable acceptance criteria — each promoted story already had them in DOCS #122, #130, #131 and #145.
- [ ] Tests added/updated (unit / integration) — no new tests this week; the existing suites were run (228, 74 and 39 tests).
- [x] DDD / hexagonal boundaries respected (domain has no I/O) — the promotion changed no code, `qa` equals `develop`.
- [x] No secrets; config via environment variables — no `.env` or key in the promoted commits.

## 6. Evidence links
- Repo: https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero
- Promotion PRs: [appointment-db #13](https://github.com/code-corhuila/barber-saas-appointment-db/pull/13) · [appointment-api #30](https://github.com/code-corhuila/barber-saas-appointment-api/pull/30) · [appointment-app #20](https://github.com/code-corhuila/barber-saas-appointment-app/pull/20) · [finance-inventory-db #10](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/10) · [finance-inventory-api #20](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/20)
- Certification log: [`certification/README.md`](../../certification/README.md)
- Record of the week (2026-11-02 to 2026-11-08, from git and GitHub): 5 PRs opened and merged, 28 commits re-applied on `qa`, 4 commits in this repo, 0 issues opened or closed, 0 comments or reviews, 0 tags.

<!-- FULL-EVIDENCE-START -->
### Commits re-applied on `qa` with `cherry-pick -x` (2026-11-05)

| Commit on `qa` | Repo | Subject | Author |
|---|---|---|---|
| [`a1b9ef6`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/a1b9ef6) | appointment-db | feat(ddl): add hour_reminder_sent_at to appointment | me |
| [`dc885ad`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/dc885ad) | appointment-db | feat(ddl): create review | me |
| [`40c909b`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/40c909b) | appointment-db | feat(ddl): keep one review per appointment | me |
| [`27fbb67`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/27fbb67) | appointment-db | feat(ddl): index the reviews of a barbershop and of a barber | me |
| [`ad49085`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/ad49085) | appointment-db | docs(readme): describe the review table and the one-hour reminder mark | me |
| [`cf2b371`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/cf2b371) | appointment-api | feat(http): read whether the token's barbershop takes bookings | me |
| [`efc987f`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/efc987f) | appointment-api | feat(booking): refuse a booking at a suspended or cancelled barbershop | me |
| [`19e53fa`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/19e53fa) | appointment-api | docs(readme): describe the suspended-barbershop rule | me |
| [`d082cbf`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/d082cbf) | appointment-api | feat(domain): add the review of a completed appointment | me |
| [`c5d2e80`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/c5d2e80) | appointment-api | feat(persistence): find one appointment of a client in any barbershop | me |
| [`bd5caac`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/bd5caac) | appointment-api | feat(review): review a completed appointment once and list a barbershop's reviews | me |
| [`3507a78`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/3507a78) | appointment-api | feat(persistence): store and read the reviews | me |
| [`bb13340`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/bb13340) | appointment-api | feat(http): read the client's name from identity-auth with the service token | me |
| [`633a56b`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/633a56b) | appointment-api | feat(http): expose the review of an appointment and the public reviews list | me |
| [`d6d6737`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/d6d6737) | appointment-api | docs(readme): describe the reviews and the public list | me |
| [`47e2b9f`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/47e2b9f) | appointment-api | feat(jobs): write the one-hour reminder once per appointment | me |
| [`5fac1ff`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/5fac1ff) | appointment-api | docs(readme): describe the one-hour reminder window | me |
| [`d9a8fe1`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/d9a8fe1) | appointment-api | feat(reminders): name the barbershop in AppointmentReminderDue | Daniel Cerquera |
| [`7e105c9`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/7e105c9) | appointment-api | feat(reviews): say whether each appointment was reviewed | Daniel Cerquera |
| [`a3de83a`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/a3de83a) | appointment-app | feat(appointment): add the review calls and show appointment-api's Spanish 422s | me |
| [`107aee8`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/107aee8) | appointment-app | feat(ui): rate a completed appointment from my appointments | me |
| [`8bf30c1`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/8bf30c1) | appointment-app | docs(readme): rate a completed appointment from my appointments | me |
| [`a8ff32b`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/a8ff32b) | appointment-app | feat(appointment): read the reviews of the barbershop page by page | me |
| [`48ccda8`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/48ccda8) | appointment-app | feat(ui): show the owner the barbershop's reviews, by barber | me |
| [`6f48f38`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/6f48f38) | appointment-app | docs(readme): add the owner's reviews screen | me |
| [`ed393ed`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/ed393ed) | appointment-app | fix(reviews): offer Calificar once, also after leaving the screen | Daniel Cerquera |
| [`271009f`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/commit/271009f) | finance-inventory-db | feat(ddl): add processed_event for the events finance consumes | Daniel Cerquera |
| [`a8aa7cf`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/commit/a8aa7cf) | finance-inventory-api | feat(events): record a completed appointment as an income | Daniel Cerquera |

### Pull requests I opened and merged

| Pull request | Opened and merged | Base | Size |
|---|---|---|---|
| [appointment-db #13](https://github.com/code-corhuila/barber-saas-appointment-db/pull/13) | 2026-11-05 | `qa` | +124 / -2, 13 files |
| [finance-inventory-db #10](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/10) | 2026-11-05 | `qa` | +29 / -0, 3 files |
| [appointment-api #30](https://github.com/code-corhuila/barber-saas-appointment-api/pull/30) | 2026-11-05 | `qa` | +1839 / -85, 51 files |
| [finance-inventory-api #20](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/20) | 2026-11-05 | `qa` | +548 / -4, 12 files |
| [appointment-app #20](https://github.com/code-corhuila/barber-saas-appointment-app/pull/20) | 2026-11-05 | `qa` | +385 / -14, 16 files |

### Commits in this repository

| Commit | Date | Subject |
|---|---|---|
| [`e449980`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e449980) | 2026-11-07 | docs(certification): add chapter 7 evidence and 3 study hours |
| [`f4d520b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f4d520b) | 2026-11-08 | docs(certification): add chapter 8 evidence and 3 study hours |
| [`a166097`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a166097) | 2026-11-08 | docs(certification): add chapter 9 evidence and 3 study hours |
| [`e6d41b7`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e6d41b7) | 2026-11-08 | docs(certification): add chapter 10 evidence and 4 study hours |

### Work outside GitHub

| Date | Work | Evidence |
|---|---|---|
| 2026-11-05 | Local test runs before each promotion (Maven, npm, Liquibase on PostgreSQL 16.4) | Output summarized in the body of each PR above |
| 2026-11-07 to 2026-11-08 | Huawei course, chapters 7 to 10 (13 hours) | [`certification/evidence/`](../../certification/evidence/) |
<!-- FULL-EVIDENCE-END -->
