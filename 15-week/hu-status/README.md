<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       15-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 15

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Take my MVP 3 work to `main` as release 3.0.0. That covers seven repositories: appointment-db/api/app, finance-inventory-db/api, loyalty-api, and the unchanged finance-inventory-app, loyalty-app and loyalty-db. It goes with a `[3.0.0]` CHANGELOG, the `git cherry-pick -x` trail, an annotated `v3.0.0` tag and a GitHub Release. Before that, close the three P1 security stories of my threat model (docs#158, docs#159, docs#160) with tests and evidence. Finish the Huawei HCCDP-Solution Architectures course: chapters 12 to 14, four labs, final exam, certificate and design note.
<!-- CONFIG-END -->

> **Week 2026-11-09 to 2026-11-15**, written on 2026-11-13. Dates are Colombia time (UTC-5). Everything below comes
> from git and GitHub: my pull requests in code-corhuila, the issues of DOCS and the commits of this repository. Test
> numbers are the `mvn -B verify` output pasted in each pull request. The organization's GitHub Actions have been
> locked by billing since 2026-10-26.

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| CHANGELOG-3.0.0 (from week 14) | As the team, we want a `[3.0.0]` section in the `CHANGELOG.md` of every repository that changed since 2.0.0, so each Release has its notes | **done — 2026-11-09, completed 2026-11-11** | To `develop`: appointment-db [#14](https://github.com/code-corhuila/barber-saas-appointment-db/pull/14), appointment-api [#31](https://github.com/code-corhuila/barber-saas-appointment-api/pull/31), appointment-app [#21](https://github.com/code-corhuila/barber-saas-appointment-app/pull/21), finance-inventory-db [#11](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/11), finance-inventory-api [#21](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/21), loyalty-api [#24](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/24). To `qa`: [#15](https://github.com/code-corhuila/barber-saas-appointment-db/pull/15), [#32](https://github.com/code-corhuila/barber-saas-appointment-api/pull/32), [#22](https://github.com/code-corhuila/barber-saas-appointment-app/pull/22), [#12](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/12), [#22](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/22), [#25](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/25). Security entries added on 2026-11-11: appointment-api [#39](https://github.com/code-corhuila/barber-saas-appointment-api/pull/39) → [#40](https://github.com/code-corhuila/barber-saas-appointment-api/pull/40) → [#41](https://github.com/code-corhuila/barber-saas-appointment-api/pull/41); loyalty-api in the team's format [#28](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/28) → [#29](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/29) → [#30](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/30) |
| HU-SEC-160 (docs#160) | As the SaaS operator, I want each outbox read capped at 50 events, so a stolen worker token cannot dump the whole outbox in one call | **done — 2026-11-10; on `main`** | appointment-api [#35](https://github.com/code-corhuila/barber-saas-appointment-api/pull/35) ([`a4baa63`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/a4baa631ce6cf552e03774d497dc4ddc2a325a29)), loyalty-api [#20](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/20) ([`e3667ea`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/e3667eac5555f6048727008f121c18180a9f3018)). Test `InternalOutboxHttpTest.theOutboxIsReadInBatchesOfAtMostFifty`: `limit=51` → `400`, `limit=0` → `400`, `limit=50` → exactly 50 events. Issue closed with evidence per criterion: [docs#160](https://github.com/code-corhuila/barber-saas-docs/issues/160#issuecomment-6778023155) |
| HU-SEC-158 (docs#158) | As the SaaS operator, I want loyalty-api to accept events only from a worker token meant for it and at most 7 days long, so a token copied from another service or a year-long token is useless | **done — 2026-11-10; on `main`** | loyalty-api [#21](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/21) ([`d440a71`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/d440a712ffc558830d6b96ab77660734f688c6c9)). Wrong `aud` → `403` and no sticker granted; `exp - iat` above 604800 s or an expired token → `401`. Tests: `InternalEventsHttpTest.aWorkerTokenMeantForAnotherServiceIs403AndGrantsNoSticker`, `anExpiredWorkerTokenOrOneLongerThanSevenDaysIs401`, `Rs256VerifierTest.a_service_token_lives_at_most_seven_days`. Closed: [docs#158](https://github.com/code-corhuila/barber-saas-docs/issues/158#issuecomment-6778020315) |
| HU-SEC-159 (docs#159) | As the SaaS operator, I want each internal operation to require its own scope (`outbox:relay`, `events:deliver`, `jobs:run`), so the worker's token cannot run an operation it was not given | **done — 2026-11-10; on `main`** | loyalty-api [#22](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/22) ([`71d6462`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/71d6462ded8923dbe57244763c13f03762428550)), appointment-api [#36](https://github.com/code-corhuila/barber-saas-appointment-api/pull/36) ([`5759709`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/5759709541aea9c674f0926c77c9c99cab4de56d)). A token with only `subscriptions:expire` → `403` on the outbox, on `POST /internal/v1/events` and on the daily jobs; with its own scope → `200`. Closed: [docs#159](https://github.com/code-corhuila/barber-saas-docs/issues/159#issuecomment-6778021787) |
| PROMO-SEC (qa + release) | As the team, we want the three security fixes promoted with their trail, so `main` gets exactly what was reviewed on `develop` | **done — 2026-11-10/11** | To `qa`: appointment-api [#37](https://github.com/code-corhuila/barber-saas-appointment-api/pull/37), loyalty-api [#23](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/23). To `release.3.0.0`: appointment-api [#38](https://github.com/code-corhuila/barber-saas-appointment-api/pull/38), loyalty-api [#26](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/26). Every commit with its `cherry picked from` line |
| REL-3.0.0 (my repositories) | As the team, we want release 3.0.0 on `main` in my repositories, each with an annotated `v3.0.0` tag and a GitHub Release whose notes are the `[3.0.0]` CHANGELOG | **done — tags and Releases on 2026-11-11 (7 repositories) and 2026-11-12 (2)** | `release.3.0.0` filled from `qa` (2026-11-09): appointment-api [#33](https://github.com/code-corhuila/barber-saas-appointment-api/pull/33) (15 commits), appointment-app [#23](https://github.com/code-corhuila/barber-saas-appointment-app/pull/23) (8), appointment-db [#16](https://github.com/code-corhuila/barber-saas-appointment-db/pull/16) (6), finance-inventory-api [#23](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/23) (2), finance-inventory-db [#13](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/13) (2). Merged to `main` after the teacher approved (2026-11-11): appointment-app [#24](https://github.com/code-corhuila/barber-saas-appointment-app/pull/24), appointment-db [#17](https://github.com/code-corhuila/barber-saas-appointment-db/pull/17), finance-inventory-api [#24](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/24), finance-inventory-db [#14](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/14). Tag and Release published by me: [appointment-app](https://github.com/code-corhuila/barber-saas-appointment-app/releases/tag/v3.0.0), [appointment-db](https://github.com/code-corhuila/barber-saas-appointment-db/releases/tag/v3.0.0), [finance-inventory-api](https://github.com/code-corhuila/barber-saas-finance-inventory-api/releases/tag/v3.0.0), [finance-inventory-db](https://github.com/code-corhuila/barber-saas-finance-inventory-db/releases/tag/v3.0.0), plus the three unchanged repositories where `main` == `qa`: [finance-inventory-app](https://github.com/code-corhuila/barber-saas-finance-inventory-app/releases/tag/v3.0.0), [loyalty-app](https://github.com/code-corhuila/barber-saas-loyalty-app/releases/tag/v3.0.0), [loyalty-db](https://github.com/code-corhuila/barber-saas-loyalty-db/releases/tag/v3.0.0). appointment-api [#34](https://github.com/code-corhuila/barber-saas-appointment-api/pull/34) and loyalty-api [#27](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/27) were merged by Daniel Cerquera on 2026-11-12, who also published their [appointment-api](https://github.com/code-corhuila/barber-saas-appointment-api/releases/tag/v3.0.0) and [loyalty-api](https://github.com/code-corhuila/barber-saas-loyalty-api/releases/tag/v3.0.0) Releases. Checked on 2026-11-13: in the 9 repositories `v3.0.0` points at `main` HEAD and `main` == `qa` |
| W13-TM (week 13 workshop) | As the team, we want my TB4 threat model (worker → `/internal/v1`) merged in DOCS, with a decision for each row | **done — merged 2026-11-11** | DOCS [#154](https://github.com/code-corhuila/barber-saas-docs/pull/154) merged after the teacher's approval; [#149](https://github.com/code-corhuila/barber-saas-docs/issues/149) closed. Its P1 rows became docs#158–#160 above; TLS and the encrypted reset code were downgraded to P3 in `05-architecture/security/row-decisions-2026-11-11.md` |
| HCCDP-C3 | As a student, I want to complete the Huawei HCCDP-Solution Architectures course, with its evidence, design note and certificate in this repository | **done — 2026-11-13: exam 85/100, certificate ICT20261114000017** | Chapter 12 [`a9ac5e8`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a9ac5e82d9bec44cadcf76d8434163c050dde538) · chapter 13 [`197da56`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/197da564ccd2eda7b150617d1f4de330dedf3745) · chapter 14, labs 1–4, final exam and certificate [`148040b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/148040bfccf762c55ad526ff462a06f9c670954a) · design note, target-architecture diagram, ADR-C3-01 and ADR-C3-02 [`f0a0e57`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f0a0e573a49219f6ccbc55d124fc83750cd7f4d1). Log: 52 hours in [`certification/README.md`](../../certification/README.md) |
| DEP-03 (my part) | As the operator, I want a readiness check in loyalty, appointment and finance-inventory | todo (unchanged) | Not started. `GET /health` already exists and the compose healthchecks use it |
| W15-SCALE | As the team, we want the booking and review journey tested against the deployed host | todo — not done by me | Daniel Cerquera ran the end-to-end script on the Azure host on 2026-11-11 (31/31) and the failure path on 2026-11-12. I did not run a load test or my own journey this week (section 3) |

## 2. My individual contribution
- **Three P1 security stories closed (2026-11-10).** All three come from my own week 13 threat model.
  - **Wrong audience:** loyalty-api now refuses a worker token meant for another service (`403`, no sticker granted).
  - **Long-lived tokens:** it also refuses a token that lives more than 7 days (`401`).
  - **Scopes:** in loyalty-api and appointment-api each internal operation requires its scope (`outbox:relay`, `events:deliver`, `jobs:run`).
  - **Outbox cap:** both services cap the outbox read at 50 events.
  - **Where the checks live:** in `Caller.requireService(service, scope)` and in the RS256 verifier. The domain stays free of I/O.
  - **Issues:** I closed docs#158, #159 and #160 with a comment that maps each acceptance criterion to its PR, commit and test.
- **Tests added.** New HTTP, verifier and use-case tests for each criterion in both services.
  - **loyalty-api:** `mvn -B verify` ends at 33 core, 29 adapters (9 JDBC tests skipped without a database) and 22 app tests.
  - **appointment-api:** 120 core, 66 adapters (22 skipped) and 45 app tests.
  - **Result:** 0 failures in both.
  - **Flaky test:** I found one in loyalty-api, `RepositoryContract.stickersAddByDeltaAndTheCardIsReadOnlyInItsBarbershop`, which fails about 1 run in 8 on `develop` before my changes. It is reported in section 3.
- **Release 3.0.0 of my repositories.**
  - **CHANGELOG:** I wrote the `[3.0.0]` CHANGELOG of six repositories from `git log` since 2.0.0.
  - **Promotion:** I promoted the CHANGELOG and the security fixes `develop` → `qa` → `release.3.0.0` with `cherry-pick -x`. I created loyalty-api's `release.3.0.0` from `main`, because it had none.
  - **PRs to `main`:** I opened the release PRs to `main` and merged with rebase the four the teacher approved.
  - **Tags:** I published 7 annotated `v3.0.0` tags with their GitHub Release, each only after its PR was merged and after checking `main` == `qa`.
  - **Overall:** 38 pull requests opened and merged across 6 repositories this week.
- **Huawei course finished.**
  - **Content:** chapters 12–14, four labs (O&M, network, database, compute and storage) and the final exam (85/100, passing 70). That is 19 hours this week and 52 in total.
  - **Design note:** BarberSaaS on Huawei Cloud, with a diagram, the five principles, scalability, DR (RTO 30 min, RPO 5 min) and the deployment pipeline.
  - **ADRs:** two, one for RDS primary/standby and one for CCE with autoscaling.
- **Authorship.** Every commit and PR is signed only by me with my git identity. The commits by teammates that I promoted keep their author.

## 3. Blockers and risks
Severity first:
- **P1 — two release pull requests merged without a GitHub review.**
  - **What happened:** appointment-api [#34](https://github.com/code-corhuila/barber-saas-appointment-api/pull/34) and loyalty-api [#27](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/27) went to `main` on 2026-11-12 in state `REVIEW_REQUIRED`. The teacher's review on #34 was dismissed when the stale-review rule ran after the CHANGELOG commit was added.
  - **Why:** the reviewer bot stopped answering, so Daniel Cerquera merged them as admin and turned the protection back on.
  - **Where it is recorded:** in his week 15 report. I state it here instead of hiding it.
  - **The tags:** they are not moved. A published tag is never deleted or rewritten.
- **P2 — GitHub Actions still locked by billing** since 2026-10-26. No CI ran on my 38 pull requests; the evidence is the local `mvn -B verify` output in each PR body.
- **P2 — flaky test in loyalty-api.** `RepositoryContract.stickersAddByDeltaAndTheCardIsReadOnlyInItsBarbershop` fails about 1 run in 8.
  - **Cause:** two rows get the same `Instant.now()` and the tie is broken by UUID.
  - **Status:** it is not caused by this week's changes and has no issue yet.
- **P2 — week 15 testing activities not done by me.** I ran no load test, Testcontainers suite or journey of my own against the host. The team's end-to-end run and failure path were done by Daniel Cerquera.
- **P3 — two items from my threat model left as P3 by the team** (`row-decisions-2026-11-11.md`).
  - **What:** TLS between the worker and the callees, and the reset code encrypted end to end.
  - **When they come back:** if a service runs on a second host.
- **P3 — docs#122 (MVP 3) is still open.** Closing it is the team's decision once the cut is graded.

## 4. Plan for next week
- Week 16 is the grading week of the cut. There is no sprint.
- Open an issue for the flaky loyalty-api test with its cause and fix (a fixed clock in the test), so it does not stay as unwritten debt.
- Ask the teacher to leave a review on appointment-api #34 and loyalty-api #27, so the merge to `main` has its approval recorded on GitHub.
- Answer any question the teacher raises on my repositories, the threat model or the HCCDP design note.

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary` — e.g. `fix(outbox): read at most fifty pending events per call`, `feat(auth): accept only short-lived worker tokens meant for loyalty`, `feat(auth): require the scope of each internal operation`, `docs(changelog): record release 3.0.0`, `docs(certification): add chapter 14, four labs, final exam and certificate`.
- [x] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...) — feature branch → PR → `develop`; `hu-*-qa` → `qa`; `hu-*-release` → `release.3.0.0`; `release.3.0.0` → `main` through the teacher's review. Nothing was pushed straight to a permanent branch, and every promotion used `git cherry-pick -x`.
- [x] Testable acceptance criteria — e.g. `limit=51` → `400`; a worker token with another `aud` → `403` and no sticker; `exp - iat` > 604800 s → `401`; a token without `outbox:relay` → `403`.
- [x] Tests added/updated (unit / integration) — new tests per criterion (HTTP, `Rs256VerifierTest`, `HandleEventsTest`, `RelayOutboxTest`). loyalty-api 33/29/22 and appointment-api 120/66/45, 0 failures (JDBC tests skipped without a database).
- [x] DDD / hexagonal boundaries respected (domain has no I/O) — the scope and audience checks live in `Caller` and the use cases; the claims are read only in the HTTP adapter (`Rs256Verifier`).
- [x] No secrets; config via environment variables — the worker tokens are issued by `dev-keys.sh` into the ignored env file; tests sign their own throwaway keys.

## 6. Evidence links
- Repo: https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero
- Security issues closed with evidence: [docs#158](https://github.com/code-corhuila/barber-saas-docs/issues/158) · [docs#159](https://github.com/code-corhuila/barber-saas-docs/issues/159) · [docs#160](https://github.com/code-corhuila/barber-saas-docs/issues/160) · threat model [DOCS #154](https://github.com/code-corhuila/barber-saas-docs/pull/154)
- Releases `v3.0.0`: [appointment-api](https://github.com/code-corhuila/barber-saas-appointment-api/releases/tag/v3.0.0) · [appointment-app](https://github.com/code-corhuila/barber-saas-appointment-app/releases/tag/v3.0.0) · [appointment-db](https://github.com/code-corhuila/barber-saas-appointment-db/releases/tag/v3.0.0) · [finance-inventory-api](https://github.com/code-corhuila/barber-saas-finance-inventory-api/releases/tag/v3.0.0) · [finance-inventory-app](https://github.com/code-corhuila/barber-saas-finance-inventory-app/releases/tag/v3.0.0) · [finance-inventory-db](https://github.com/code-corhuila/barber-saas-finance-inventory-db/releases/tag/v3.0.0) · [loyalty-api](https://github.com/code-corhuila/barber-saas-loyalty-api/releases/tag/v3.0.0) · [loyalty-app](https://github.com/code-corhuila/barber-saas-loyalty-app/releases/tag/v3.0.0) · [loyalty-db](https://github.com/code-corhuila/barber-saas-loyalty-db/releases/tag/v3.0.0)
- Certification: [`certification/README.md`](../../certification/README.md) · [`certificate.pdf`](../../certification/certificate.pdf) · [design note](../../certification/design/architecture-design-note.md) · [ADR-C3-01](../../certification/design/ADR-C3-01.md) · [ADR-C3-02](../../certification/design/ADR-C3-02.md)
- Record of the week (2026-11-09 to 2026-11-13, from git and GitHub): 38 pull requests opened by me and merged (36 merged by me, 2 by Daniel Cerquera), 7 tags with their Release published, 3 issues closed with evidence, 5 comments, 4 commits in this repository.

<!-- FULL-EVIDENCE-START -->
### Complete evidence of the week

#### My commits on `develop` (the reviewed originals)

| Commit | Date | Repo | Subject | Pull request |
|---|---|---|---|---|
| [`a4baa63`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/a4baa631ce6cf552e03774d497dc4ddc2a325a29) | 2026-11-10 | appointment-api | fix(outbox): read at most fifty pending events per call | [#35](https://github.com/code-corhuila/barber-saas-appointment-api/pull/35) +37/-10 |
| [`5759709`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/5759709541aea9c674f0926c77c9c99cab4de56d) | 2026-11-10 | appointment-api | feat(auth): require the scope of each internal worker operation | [#36](https://github.com/code-corhuila/barber-saas-appointment-api/pull/36) +97/-13 |
| [`bfab514`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/bfab514ac6e410926a790641ece2f5fb12e24d82) | 2026-11-11 | appointment-api | docs(changelog): record the security fixes of docs#159 and docs#160 in 3.0.0 | [#39](https://github.com/code-corhuila/barber-saas-appointment-api/pull/39) |
| [`e3667ea`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/e3667eac5555f6048727008f121c18180a9f3018) | 2026-11-10 | loyalty-api | fix(outbox): read at most fifty pending events per call | [#20](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/20) +27/-3 |
| [`d440a71`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/d440a712ffc558830d6b96ab77660734f688c6c9) | 2026-11-10 | loyalty-api | feat(auth): accept only short-lived worker tokens meant for loyalty | [#21](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/21) +132/-9 |
| [`71d6462`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/71d6462ded8923dbe57244763c13f03762428550) | 2026-11-10 | loyalty-api | feat(auth): require the scope of each internal operation | [#22](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/22) +105/-19 |
| [`6696074`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/6696074df254c95901f3b1355447aa4a5a0540fa) | 2026-11-10 | loyalty-api | docs(changelog): record release 3.0.0 | [#24](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/24) |
| [`bc0db5c`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/bc0db5c22448b884f52a50e88d28095b26705863) | 2026-11-11 | loyalty-api | docs(changelog): use the release format of the other repos in 3.0.0 | [#28](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/28) |

The `docs(changelog): record release 3.0.0` commits of appointment-db, appointment-app, finance-inventory-db and
finance-inventory-api went in through [#14](https://github.com/code-corhuila/barber-saas-appointment-db/pull/14),
[#21](https://github.com/code-corhuila/barber-saas-appointment-app/pull/21),
[#11](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/11) and
[#21](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/21) on 2026-11-09.

#### The same commits on `main` (re-applied with `cherry-pick -x`)

| Commit on `main` | Repo | Subject |
|---|---|---|
| [`e6add50`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/e6add50479d2b3754a2d0636ab937484eccc187c) | appointment-api | fix(outbox): read at most fifty pending events per call |
| [`b70032b`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/b70032bd500f055a1194c6b8207d6bc9026e315c) | appointment-api | feat(auth): require the scope of each internal worker operation |
| [`509c58c`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/509c58c0ac820f5ac96e856915b9cc16ff5a4586) | appointment-api | docs(changelog): record release 3.0.0 |
| [`c63f29c`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/c63f29c55587e4d32d4395c889efeb8ad971d853) | appointment-api | docs(changelog): record the security fixes of docs#159 and docs#160 in 3.0.0 (= tag `v3.0.0`) |
| [`0db9f0b`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/0db9f0bfccc7bbc14e2b5110ad1d3dbf56c92b97) | loyalty-api | fix(outbox): read at most fifty pending events per call |
| [`8fe5249`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/8fe52496ee6e3c2c782294cc5dd6b25045166191) | loyalty-api | feat(auth): accept only short-lived worker tokens meant for loyalty |
| [`5eeb9c2`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/5eeb9c29902d9278fe2ebf194b3190d4e7c39a85) | loyalty-api | feat(auth): require the scope of each internal operation |
| [`b339409`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/b339409c04ccd7a2c5c8676abb2b0bc657024d01) | loyalty-api | docs(changelog): record release 3.0.0 |
| [`4f67d2c`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/4f67d2c6f057a52ad7e8897657ebea2809208947) | loyalty-api | docs(changelog): use the release format of the other repos in 3.0.0 (= tag `v3.0.0`) |
| [`b1b8069`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/b1b80693e7c3da652a2a674f079da12f47d835e5) | appointment-app | docs(changelog): record release 3.0.0 (= tag `v3.0.0`) |
| [`4676df5`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/4676df5a52e078760f2a0bbc48a890181958649e) | appointment-db | docs(changelog): record release 3.0.0 (= tag `v3.0.0`) |
| [`fb47d67`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/commit/fb47d679a5bd1d71e2502bdefac8f2156100bbd8) | finance-inventory-api | docs(changelog): record release 3.0.0 (= tag `v3.0.0`) |
| [`3552bac`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/commit/3552bac2e46fa5080c3c7f82edd79d4892756199) | finance-inventory-db | docs(changelog): record release 3.0.0 (= tag `v3.0.0`) |

#### Pull requests I opened and merged

| Date | Pull request | Base | What |
|---|---|---|---|
| 2026-11-09 | [appointment-db #14](https://github.com/code-corhuila/barber-saas-appointment-db/pull/14), [appointment-api #31](https://github.com/code-corhuila/barber-saas-appointment-api/pull/31), [appointment-app #21](https://github.com/code-corhuila/barber-saas-appointment-app/pull/21), [finance-inventory-db #11](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/11), [finance-inventory-api #21](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/21) | `develop` | `[3.0.0]` CHANGELOG |
| 2026-11-09 | [#15](https://github.com/code-corhuila/barber-saas-appointment-db/pull/15), [#32](https://github.com/code-corhuila/barber-saas-appointment-api/pull/32), [#22](https://github.com/code-corhuila/barber-saas-appointment-app/pull/22), [#12](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/12), [#22](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/22) | `qa` | CHANGELOG promoted |
| 2026-11-09 | [appointment-api #33](https://github.com/code-corhuila/barber-saas-appointment-api/pull/33), [appointment-app #23](https://github.com/code-corhuila/barber-saas-appointment-app/pull/23), [appointment-db #16](https://github.com/code-corhuila/barber-saas-appointment-db/pull/16), [finance-inventory-api #23](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/23), [finance-inventory-db #13](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/13) | `release.3.0.0` | release filled from `qa` (33 commits) |
| 2026-11-10 | [appointment-api #35](https://github.com/code-corhuila/barber-saas-appointment-api/pull/35), [#36](https://github.com/code-corhuila/barber-saas-appointment-api/pull/36), [loyalty-api #20](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/20), [#21](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/21), [#22](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/22) | `develop` | docs#158, #159, #160 |
| 2026-11-10 | [appointment-api #37](https://github.com/code-corhuila/barber-saas-appointment-api/pull/37), [loyalty-api #23](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/23) | `qa` | security fixes promoted |
| 2026-11-10 | [appointment-api #38](https://github.com/code-corhuila/barber-saas-appointment-api/pull/38) | `release.3.0.0` | security fixes into the release |
| 2026-11-11 | [appointment-api #39](https://github.com/code-corhuila/barber-saas-appointment-api/pull/39) → [#40](https://github.com/code-corhuila/barber-saas-appointment-api/pull/40) → [#41](https://github.com/code-corhuila/barber-saas-appointment-api/pull/41) | `develop` → `qa` → `release.3.0.0` | CHANGELOG with #159 and #160 |
| 2026-11-10/11 | [loyalty-api #24](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/24) → [#25](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/25) → [#26](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/26); [#28](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/28) → [#29](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/29) → [#30](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/30) | `develop` → `qa` → `release.3.0.0` | loyalty CHANGELOG and its release branch |
| 2026-11-11 | [appointment-app #24](https://github.com/code-corhuila/barber-saas-appointment-app/pull/24), [appointment-db #17](https://github.com/code-corhuila/barber-saas-appointment-db/pull/17), [finance-inventory-api #24](https://github.com/code-corhuila/barber-saas-finance-inventory-api/pull/24), [finance-inventory-db #14](https://github.com/code-corhuila/barber-saas-finance-inventory-db/pull/14) | `main` | release 3.0.0, approved by the teacher |
| opened 2026-11-10/11, merged 2026-11-12 by Daniel Cerquera | [appointment-api #34](https://github.com/code-corhuila/barber-saas-appointment-api/pull/34), [loyalty-api #27](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/27) | `main` | release 3.0.0 (section 3, P1) |

#### Tags and GitHub Releases I published (2026-11-11)

| Repository | Tag | Points at |
|---|---|---|
| appointment-app | [`v3.0.0`](https://github.com/code-corhuila/barber-saas-appointment-app/releases/tag/v3.0.0) | `main` after #24 |
| appointment-db | [`v3.0.0`](https://github.com/code-corhuila/barber-saas-appointment-db/releases/tag/v3.0.0) | `main` after #17 |
| finance-inventory-api | [`v3.0.0`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/releases/tag/v3.0.0) | `main` after #24 |
| finance-inventory-db | [`v3.0.0`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/releases/tag/v3.0.0) | `main` after #14 |
| finance-inventory-app | [`v3.0.0`](https://github.com/code-corhuila/barber-saas-finance-inventory-app/releases/tag/v3.0.0) | `main` == `qa`, no change since 2.0.0 |
| loyalty-app | [`v3.0.0`](https://github.com/code-corhuila/barber-saas-loyalty-app/releases/tag/v3.0.0) | `main` == `qa`, no change since 2.0.0 |
| loyalty-db | [`v3.0.0`](https://github.com/code-corhuila/barber-saas-loyalty-db/releases/tag/v3.0.0) | `main` == `qa`, no change since 2.0.0 |

Each tag is annotated with "Release v3.0.0: MVP 3 (corte 3)", and the trail line points to `code-corhuila/barber-saas-docs#122`.
No Release is a draft or a pre-release.

#### Comments with evidence

| Date | Where |
|---|---|
| 2026-11-10 | [docs#158](https://github.com/code-corhuila/barber-saas-docs/issues/158#issuecomment-6778020315), [docs#159](https://github.com/code-corhuila/barber-saas-docs/issues/159#issuecomment-6778021787), [docs#160](https://github.com/code-corhuila/barber-saas-docs/issues/160#issuecomment-6778023155): evidence per acceptance criterion, then closed |
| 2026-11-10 | [appointment-api #34](https://github.com/code-corhuila/barber-saas-appointment-api/pull/34#issuecomment-6777925004): the security commits added to the release, for the teacher's review |
| 2026-11-11 | [appointment-api #34](https://github.com/code-corhuila/barber-saas-appointment-api/pull/34#issuecomment-6809794857): the CHANGELOG commit added, with a request for a new approval |

#### Commits in this repository

| Commit | Date | Subject |
|---|---|---|
| [`a9ac5e8`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a9ac5e82d9bec44cadcf76d8434163c050dde538) | 2026-11-10 | docs(certification): add chapter 12 evidence and 2 study hours |
| [`197da56`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/197da564ccd2eda7b150617d1f4de330dedf3745) | 2026-11-10 | docs(certification): add chapter 13 evidence and 3 study hours |
| [`148040b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/148040bfccf762c55ad526ff462a06f9c670954a) | 2026-11-13 | docs(certification): add chapter 14, four labs, final exam and certificate |
| [`f0a0e57`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f0a0e573a49219f6ccbc55d124fc83750cd7f4d1) | 2026-11-13 | docs(certification): fill labs, skills summary, design note and ADRs |

#### Work outside GitHub

| Date | Work | Evidence |
|---|---|---|
| 2026-11-10 | `mvn -B verify` of loyalty-api and appointment-api before each merge (JDBC tests skipped without a database) | Output pasted in [#20](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/20), [#21](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/21), [#22](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/22), [#35](https://github.com/code-corhuila/barber-saas-appointment-api/pull/35), [#36](https://github.com/code-corhuila/barber-saas-appointment-api/pull/36) |
| 2026-11-10 to 2026-11-13 | Huawei HCCDP course: chapters 12–14, four labs, final exam (19 hours this week) | [`certification/evidence/`](../../certification/evidence/) |
<!-- FULL-EVIDENCE-END -->
