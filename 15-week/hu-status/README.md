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
| HCCDP-C3 | As a student, I want to take the Huawei course of the third cut and keep its evidence in this repository | **done in this repository — 2026-11-13; the Moodle upload of the certification is still mine to do before 2026-11-15 23:59** | Course 100 % completed, final exam 85/100 (passing 70), certificate ICT20261114000017 issued 2026-11-14: [`certificate.pdf`](../../certification/certificate.pdf). Log of 52 hours in [`certification/README.md`](../../certification/README.md). Commits since 2026-11-07, one by one: [`e449980`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e449980298e78a2bb5ae3f6e7b2e49b280829d03) ch. 7, 2026-11-07 · [`f4d520b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f4d520b76a4b8899f28f112401b500e8b620454d) ch. 8, 2026-11-08 · [`a166097`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a166097b749d60ad8381c482aa2d580c1c3cf86f) ch. 9, 2026-11-08 · [`e6d41b7`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e6d41b77a765f92c9dc2879f14fdfffce1a84eaf) ch. 10, 2026-11-08 · [`a9fbcdd`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a9fbcdd10c2141b49ef7f6dd2f27ff6d579d4e99) ch. 9 hours fixed, 2026-11-08 · [`2eb6fd4`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/2eb6fd4254f4153b760b6766eb9fe511d2766de4) ch. 11, 2026-11-08 · [`a9ac5e8`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a9ac5e82d9bec44cadcf76d8434163c050dde538) ch. 12, 2026-11-10 · [`197da56`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/197da564ccd2eda7b150617d1f4de330dedf3745) ch. 13, 2026-11-10 · [`148040b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/148040bfccf762c55ad526ff462a06f9c670954a) ch. 14, labs 1-4, final exam and certificate, 2026-11-13 · [`f0a0e57`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f0a0e573a49219f6ccbc55d124fc83750cd7f4d1) labs table, skills summary, design note, diagram, ADR-C3-01 and ADR-C3-02, 2026-11-13. The earlier commits (enrollment and chapters 1-6, 2026-10-26 to 2026-11-01) belong to weeks 12 and 13 |
| FINAL-C3 (team) | As the team, we want to present BarberSaaS from the problem to the lessons with a live demo on the deployed host, and deliver `c3-release` and `c3-project` | **done — 2026-11-12 (team)** | Presented on 2026-11-12 with the live demo on https://barbersaas.chilecentral.cloudapp.azure.com (the Azure VM of ADR-020). Deck: [`BarberSaaS-Sustentacion-Final.pdf`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/15-week/hu-status/BarberSaaS-Sustentacion-Final.pdf). `c3-release` and `c3-project` were uploaded to Moodle on 2026-11-12 by Daniel Cerquera for the team ([his week 15 report](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/15-week/hu-status/README.md), HU-000-095, HU-000-096, HU-000-098). My part in the delivered code is the `v3.0.0` Releases of appointment, finance-inventory and loyalty listed above |
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
- Record of the week (2026-11-09 to 2026-11-13, from git and GitHub): 38 pull requests opened by me and merged (36 merged by me, 2 by Daniel Cerquera), 7 tags with their Release published, 3 issues closed with evidence, 6 comments, 12 commits in this repository since 2026-11-07.

<!-- FULL-EVIDENCE-START -->
### Complete evidence of the week, item by item (from git and GitHub, since 2026-11-07)

**Totals:**
- 106 commits of mine across `develop`, `qa`, `release.3.0.0` and `main` of 6 code repositories. 8 of them are Daniel Cerquera's commits that I re-applied with `cherry-pick -x`; he stays their author.
- 3 commits on `main` of DOCS and 12 commits in this repository.
- 38 pull requests opened: 36 merged by me, 2 by Daniel Cerquera.
- 7 tags with their GitHub Release, 3 issues closed, 6 comments and 0 reviews.

Each date is the day the commit landed on that branch. The release fill of 2026-11-09 re-applied commits first written on 2026-10-26.

#### Every commit of mine on `develop` (13)

| Commit | Date | Subject | Author |
|---|---|---|---|
| [appointment-api `7a22e71`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/7a22e71b1d2f4d0a9c1d1664af7d109a938e83a8) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-app `051de81`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/051de817438b6665bef65fb4de7200b0c9d57c58) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-db `1606c70`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/1606c704cde9ecb1d4de429559be8d8278efdcf3) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [finance-inventory-api `3a6b385`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/commit/3a6b385627795b6f91f987bbf60f3be69a95bc59) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [finance-inventory-db `f42b6a1`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/commit/f42b6a149f247ae7afd2bb93218996cd9ac18a27) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-api `5759709`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/5759709541aea9c674f0926c77c9c99cab4de56d) | 2026-11-10 | feat(auth): require the scope of each internal worker operation | me |
| [appointment-api `a4baa63`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/a4baa631ce6cf552e03774d497dc4ddc2a325a29) | 2026-11-10 | fix(outbox): read at most fifty pending events per call | me |
| [loyalty-api `71d6462`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/71d6462ded8923dbe57244763c13f03762428550) | 2026-11-10 | feat(auth): require the scope of each internal operation | me |
| [loyalty-api `d440a71`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/d440a712ffc558830d6b96ab77660734f688c6c9) | 2026-11-10 | feat(auth): accept only short-lived worker tokens meant for loyalty | me |
| [loyalty-api `e3667ea`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/e3667eac5555f6048727008f121c18180a9f3018) | 2026-11-10 | fix(outbox): read at most fifty pending events per call | me |
| [appointment-api `bfab514`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/bfab514ac6e410926a790641ece2f5fb12e24d82) | 2026-11-11 | docs(changelog): record the security fixes of docs#159 and docs#160 in 3.0.0 | me |
| [loyalty-api `bc0db5c`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/bc0db5c22448b884f52a50e88d28095b26705863) | 2026-11-11 | docs(changelog): use the release format of the other repos in 3.0.0 | me |
| [loyalty-api `6696074`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/6696074df254c95901f3b1355447aa4a5a0540fa) | 2026-11-11 | docs(changelog): record release 3.0.0 | me |

#### Every commit of mine on `qa` (13)

| Commit | Date | Subject | Author |
|---|---|---|---|
| [appointment-api `7e4b349`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/7e4b3493d70c9270c5ef3376eed086ad258616f9) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-app `99f2e99`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/99f2e999d9cec6e638e97a21678d247a4707cd88) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-db `bb0d4a2`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/bb0d4a2010c3b2e4805182804985edc02b2a2a14) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [finance-inventory-api `058bbe7`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/commit/058bbe7560345dcaaf682070ea375aeeccef26cf) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [finance-inventory-db `9a9c1fd`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/commit/9a9c1fdc464d6cbcf775359a9b72917c0a0a5b43) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-api `d8cfd64`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/d8cfd64b873496667fdd15e6576ccd81c25d2dba) | 2026-11-10 | feat(auth): require the scope of each internal worker operation | me |
| [appointment-api `1442203`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/14422032aa76cd0af78ed254af6c42a31b9078d8) | 2026-11-10 | fix(outbox): read at most fifty pending events per call | me |
| [loyalty-api `d1187e3`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/d1187e3c8b8f644e588c5cd4cdf53ccb0c232618) | 2026-11-10 | feat(auth): require the scope of each internal operation | me |
| [loyalty-api `a82b67a`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/a82b67a755172385e3126f4e245c57c69c5e215e) | 2026-11-10 | feat(auth): accept only short-lived worker tokens meant for loyalty | me |
| [loyalty-api `77da8e9`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/77da8e9b656421d8b00f96889ec93e034e1601df) | 2026-11-10 | fix(outbox): read at most fifty pending events per call | me |
| [appointment-api `39106d4`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/39106d4bbddb4a73a4a968516fc4e71a848f339d) | 2026-11-11 | docs(changelog): record the security fixes of docs#159 and docs#160 in 3.0.0 | me |
| [loyalty-api `299d183`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/299d183c18d793d837328693f999749f9b581169) | 2026-11-11 | docs(changelog): use the release format of the other repos in 3.0.0 | me |
| [loyalty-api `f0c57d3`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/f0c57d350ae9a6a8980f7953c47cac361dd6d701) | 2026-11-11 | docs(changelog): record release 3.0.0 | me |

#### Every commit of mine on `release.3.0.0` (41)

| Commit | Date | Subject | Author |
|---|---|---|---|
| [appointment-api `1976c2a`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/1976c2af4d098054c96f9942a5bc3e597bd1ab20) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-api `914d49c`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/914d49c4513a94ebf7ab654dc8432a9c1ddd6fe2) | 2026-11-09 | feat(reviews): say whether each appointment was reviewed | Daniel Cerquera |
| [appointment-api `5e2ecb0`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/5e2ecb069d16d6f7f745a7d7a876356ce911d2a2) | 2026-11-09 | feat(reminders): name the barbershop in AppointmentReminderDue | Daniel Cerquera |
| [appointment-api `0ac1563`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/0ac15636e65ea9b4e2cf36ce952869e1049c03f5) | 2026-11-09 | docs(readme): describe the one-hour reminder window | me |
| [appointment-api `6ef2d1c`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/6ef2d1c0f1e8bc9664f6992fc19bcd2bd227072a) | 2026-11-09 | feat(jobs): write the one-hour reminder once per appointment | me |
| [appointment-api `b1b01b0`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/b1b01b0001cb18df5c4729385d52f1d1841fa4e1) | 2026-11-09 | docs(readme): describe the reviews and the public list | me |
| [appointment-api `1299037`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/129903715c8d42c949720add3f679f4d5a329cb8) | 2026-11-09 | feat(http): expose the review of an appointment and the public reviews list | me |
| [appointment-api `140699d`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/140699d7c463f255883ab899ebc4bfc53314827d) | 2026-11-09 | feat(http): read the client's name from identity-auth with the service token | me |
| [appointment-api `c1dbe57`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/c1dbe57048386362459a89df90c8dcbd9fc630a4) | 2026-11-09 | feat(persistence): store and read the reviews | me |
| [appointment-api `baaeebc`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/baaeebc326857a9f092c5b7cf3642e740fdeabd6) | 2026-11-09 | feat(review): review a completed appointment once and list a barbershop's reviews | me |
| [appointment-api `c81e3f8`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/c81e3f8409dad3503c8987682dfe0b5d79d171fb) | 2026-11-09 | feat(persistence): find one appointment of a client in any barbershop | me |
| [appointment-api `2360216`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/236021678ad843b80e0ac2e58e5c88c18fad4b05) | 2026-11-09 | feat(domain): add the review of a completed appointment | me |
| [appointment-api `d44e2c6`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/d44e2c64f00e260a919f1587effbce19d5672d41) | 2026-11-09 | docs(readme): describe the suspended-barbershop rule | me |
| [appointment-api `336372d`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/336372d67034be85d36c5643573a8b2d82e6a5eb) | 2026-11-09 | feat(booking): refuse a booking at a suspended or cancelled barbershop | me |
| [appointment-api `45331a0`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/45331a0b04860e36ebbf14bb550377c5ce873746) | 2026-11-09 | feat(http): read whether the token's barbershop takes bookings | me |
| [appointment-app `eb7514a`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/eb7514a408417450409263c233ce90fc25814d68) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-app `41493a0`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/41493a01a5a0d50bcb5d475a539159d3e5ed54d4) | 2026-11-09 | fix(reviews): offer Calificar once, also after leaving the screen | Daniel Cerquera |
| [appointment-app `bfa32c1`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/bfa32c1a888af46630486de940bdf016c7118b78) | 2026-11-09 | docs(readme): add the owner's reviews screen | me |
| [appointment-app `845074e`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/845074e10893d0fccf69305edf841eb4efe79457) | 2026-11-09 | feat(ui): show the owner the barbershop's reviews, by barber | me |
| [appointment-app `207eded`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/207eded1a63f9bca6f6f05ec540994bf4f224044) | 2026-11-09 | feat(appointment): read the reviews of the barbershop page by page | me |
| [appointment-app `b37c15a`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/b37c15a7d30bc5dcb4ff07a2e4f2417e3db55a58) | 2026-11-09 | docs(readme): rate a completed appointment from my appointments | me |
| [appointment-app `1f94ea0`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/1f94ea0838a536651b2b2c85680777d9391df4c7) | 2026-11-09 | feat(ui): rate a completed appointment from my appointments | me |
| [appointment-app `2f6ae33`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/2f6ae336a718c01c4d9863d47003662214acd95c) | 2026-11-09 | feat(appointment): add the review calls and show appointment-api's Spanish 422s | me |
| [appointment-db `90a19eb`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/90a19ebfb75c910f030f2c1220fb72830a733ee8) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [appointment-db `9e86d62`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/9e86d62c7c56d2bfd1634022c3309e20c6ec9161) | 2026-11-09 | docs(readme): describe the review table and the one-hour reminder mark | me |
| [appointment-db `9231212`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/9231212ec2c1304715f900af2c44068b5c9dcce4) | 2026-11-09 | feat(ddl): index the reviews of a barbershop and of a barber | me |
| [appointment-db `ea47ea4`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/ea47ea4cd71e1e99c2a0b01b010319abf99f5057) | 2026-11-09 | feat(ddl): keep one review per appointment | me |
| [appointment-db `7639ae1`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/7639ae1ca414bf5c09929e49b1859ae9ea5a5407) | 2026-11-09 | feat(ddl): create review | me |
| [appointment-db `19721c5`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/19721c59b8ad820befb2d71225684adf1707e397) | 2026-11-09 | feat(ddl): add hour_reminder_sent_at to appointment | me |
| [finance-inventory-api `939772a`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/commit/939772a043c5b04b8c520a6f16a384f57004fb00) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [finance-inventory-api `472e390`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/commit/472e39082b5fd05b0cfa509b87d53778067ed65b) | 2026-11-09 | feat(events): record a completed appointment as an income | Daniel Cerquera |
| [finance-inventory-db `d1ab302`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/commit/d1ab3028b666e237c7d14f286b077719a03ae81f) | 2026-11-09 | docs(changelog): record release 3.0.0 | me |
| [finance-inventory-db `53b868f`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/commit/53b868f1b394a048e63a6a083d638dd9a6c85ac2) | 2026-11-09 | feat(ddl): add processed_event for the events finance consumes | Daniel Cerquera |
| [appointment-api `6be8a12`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/6be8a12a461a57154abd835b4431fe7184fe7b38) | 2026-11-10 | feat(auth): require the scope of each internal worker operation | me |
| [appointment-api `3d03a39`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/3d03a39f7318e9fa973be0aca274e444c5aece4c) | 2026-11-10 | fix(outbox): read at most fifty pending events per call | me |
| [appointment-api `9a892a3`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/9a892a36f3bcf41b7ac23a4462c4a023f52c7d05) | 2026-11-11 | docs(changelog): record the security fixes of docs#159 and docs#160 in 3.0.0 | me |
| [loyalty-api `cfc8822`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/cfc882233935c1d115bbb06c1d65a90c3f7f7faa) | 2026-11-11 | docs(changelog): use the release format of the other repos in 3.0.0 | me |
| [loyalty-api `8eb916c`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/8eb916c7db9ac83e92fb2e136e942c889a5d52cf) | 2026-11-11 | docs(changelog): record release 3.0.0 | me |
| [loyalty-api `5a2015b`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/5a2015bb28fc9a0c358ee09821fbf3f18066384b) | 2026-11-11 | feat(auth): require the scope of each internal operation | me |
| [loyalty-api `7af8a48`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/7af8a48c3aeef8e2f3569f7de279a481968f641b) | 2026-11-11 | feat(auth): accept only short-lived worker tokens meant for loyalty | me |
| [loyalty-api `46754e3`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/46754e3893c1ba94fb88e8f6b00d73aa73ed509d) | 2026-11-11 | fix(outbox): read at most fifty pending events per call | me |

#### Every commit of mine on `main` (39)

| Commit | Date | Subject | Author |
|---|---|---|---|
| [appointment-app `b1b8069`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/b1b80693e7c3da652a2a674f079da12f47d835e5) | 2026-11-11 | docs(changelog): record release 3.0.0 | me |
| [appointment-app `7a0a085`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/7a0a085f355c9ecc7fac3052d39a1978af1aa144) | 2026-11-11 | fix(reviews): offer Calificar once, also after leaving the screen | Daniel Cerquera |
| [appointment-app `a534dca`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/a534dca611bc0ea03c8a53d01cd45afb3f4d06d8) | 2026-11-11 | docs(readme): add the owner's reviews screen | me |
| [appointment-app `7b47e9a`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/7b47e9ae361d01dfa710a3157e9f3e0cb83fac89) | 2026-11-11 | feat(ui): show the owner the barbershop's reviews, by barber | me |
| [appointment-app `7fec764`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/7fec76444b97d59a8b263d391ba2dd4483132d52) | 2026-11-11 | feat(appointment): read the reviews of the barbershop page by page | me |
| [appointment-app `c873fb6`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/c873fb61fea8c01f1f666649dc1df5cb3facfb67) | 2026-11-11 | docs(readme): rate a completed appointment from my appointments | me |
| [appointment-app `773e9ae`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/773e9aec6717d628356a7510c9a3af71ec243a3f) | 2026-11-11 | feat(ui): rate a completed appointment from my appointments | me |
| [appointment-app `4c5314b`](https://github.com/code-corhuila/barber-saas-appointment-app/commit/4c5314b0ce92697b01a307ac93d182b1f023a976) | 2026-11-11 | feat(appointment): add the review calls and show appointment-api's Spanish 422s | me |
| [appointment-db `4676df5`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/4676df5a52e078760f2a0bbc48a890181958649e) | 2026-11-11 | docs(changelog): record release 3.0.0 | me |
| [appointment-db `75a7f3c`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/75a7f3c60f7d3a6ed0a3d94a2f04154a42fb9c4f) | 2026-11-11 | docs(readme): describe the review table and the one-hour reminder mark | me |
| [appointment-db `d5edc9a`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/d5edc9a86f25a409433256c6e9ef94e5272a3ef9) | 2026-11-11 | feat(ddl): index the reviews of a barbershop and of a barber | me |
| [appointment-db `f4fb11f`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/f4fb11f04a069f41a631bfd9f2a15aa831c7853b) | 2026-11-11 | feat(ddl): keep one review per appointment | me |
| [appointment-db `0cbf3f3`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/0cbf3f301ccea28c5d5db642d612e45c6345ec16) | 2026-11-11 | feat(ddl): create review | me |
| [appointment-db `f91b8e3`](https://github.com/code-corhuila/barber-saas-appointment-db/commit/f91b8e34b8b6a3b117dbd1c943b516846e9cfb34) | 2026-11-11 | feat(ddl): add hour_reminder_sent_at to appointment | me |
| [finance-inventory-api `fb47d67`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/commit/fb47d679a5bd1d71e2502bdefac8f2156100bbd8) | 2026-11-11 | docs(changelog): record release 3.0.0 | me |
| [finance-inventory-api `77777df`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/commit/77777dfddeeff8fedafdb71317491b63a1584d44) | 2026-11-11 | feat(events): record a completed appointment as an income | Daniel Cerquera |
| [finance-inventory-db `3552bac`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/commit/3552bac2e46fa5080c3c7f82edd79d4892756199) | 2026-11-11 | docs(changelog): record release 3.0.0 | me |
| [finance-inventory-db `058433b`](https://github.com/code-corhuila/barber-saas-finance-inventory-db/commit/058433b1654ea8bd4591f93a553bc1dfe45df13a) | 2026-11-11 | feat(ddl): add processed_event for the events finance consumes | Daniel Cerquera |
| [appointment-api `c63f29c`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/c63f29c55587e4d32d4395c889efeb8ad971d853) | 2026-11-12 | docs(changelog): record the security fixes of docs#159 and docs#160 in 3.0.0 | me |
| [appointment-api `b70032b`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/b70032bd500f055a1194c6b8207d6bc9026e315c) | 2026-11-12 | feat(auth): require the scope of each internal worker operation | me |
| [appointment-api `e6add50`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/e6add50479d2b3754a2d0636ab937484eccc187c) | 2026-11-12 | fix(outbox): read at most fifty pending events per call | me |
| [appointment-api `509c58c`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/509c58c0ac820f5ac96e856915b9cc16ff5a4586) | 2026-11-12 | docs(changelog): record release 3.0.0 | me |
| [appointment-api `6bf98d9`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/6bf98d904eff86b2a94132e4aa2a81fda7ea33c9) | 2026-11-12 | docs(readme): describe the one-hour reminder window | me |
| [appointment-api `daa7073`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/daa70736c1f47c3c1cdcd265df9aaa6ef7fad057) | 2026-11-12 | feat(jobs): write the one-hour reminder once per appointment | me |
| [appointment-api `2f2f3d7`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/2f2f3d76c7c14d89280083494eb385a063c0e071) | 2026-11-12 | docs(readme): describe the reviews and the public list | me |
| [appointment-api `d53de2d`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/d53de2d314b3975ae4e0b67686c25d181923d8ca) | 2026-11-12 | feat(http): expose the review of an appointment and the public reviews list | me |
| [appointment-api `c66f875`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/c66f875b4fa65f45c7aea192c6a70044ca2766fc) | 2026-11-12 | feat(http): read the client's name from identity-auth with the service token | me |
| [appointment-api `784ad37`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/784ad37641e8bd3520c4c8b56330baed01dbd344) | 2026-11-12 | feat(persistence): store and read the reviews | me |
| [appointment-api `98de267`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/98de2678ed1a270a8f804feed3e44150ac304ab6) | 2026-11-12 | feat(review): review a completed appointment once and list a barbershop's reviews | me |
| [appointment-api `45aab30`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/45aab3013be86e3105acf01600a74dc56ad60d10) | 2026-11-12 | feat(persistence): find one appointment of a client in any barbershop | me |
| [appointment-api `2bee8ab`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/2bee8ab0c9bec8d08fe7d1cc2da4cff21a7ca6f3) | 2026-11-12 | feat(domain): add the review of a completed appointment | me |
| [appointment-api `6b5bc0f`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/6b5bc0fe0ff60df97bf15d69e9688f1834990f0c) | 2026-11-12 | docs(readme): describe the suspended-barbershop rule | me |
| [appointment-api `b7003cd`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/b7003cdc01751309ad3d4862b1f1304a0501dcd8) | 2026-11-12 | feat(booking): refuse a booking at a suspended or cancelled barbershop | me |
| [appointment-api `672955d`](https://github.com/code-corhuila/barber-saas-appointment-api/commit/672955d65ac59762ed807a4a09ce2bb54b92ce12) | 2026-11-12 | feat(http): read whether the token's barbershop takes bookings | me |
| [loyalty-api `4f67d2c`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/4f67d2c6f057a52ad7e8897657ebea2809208947) | 2026-11-12 | docs(changelog): use the release format of the other repos in 3.0.0 | me |
| [loyalty-api `b339409`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/b339409c04ccd7a2c5c8676abb2b0bc657024d01) | 2026-11-12 | docs(changelog): record release 3.0.0 | me |
| [loyalty-api `5eeb9c2`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/5eeb9c29902d9278fe2ebf194b3190d4e7c39a85) | 2026-11-12 | feat(auth): require the scope of each internal operation | me |
| [loyalty-api `8fe5249`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/8fe52496ee6e3c2c782294cc5dd6b25045166191) | 2026-11-12 | feat(auth): accept only short-lived worker tokens meant for loyalty | me |
| [loyalty-api `0db9f0b`](https://github.com/code-corhuila/barber-saas-loyalty-api/commit/0db9f0bfccc7bbc14e2b5110ad1d3dbf56c92b97) | 2026-11-12 | fix(outbox): read at most fifty pending events per call | me |

#### My commits on `main` of DOCS

| Commit | Date | Subject |
|---|---|---|
| [`3307006`](https://github.com/code-corhuila/barber-saas-docs/commit/3307006) | 2026-11-11 | docs(security): STRIDE context and first threats for worker internal relay |
| [`7e7930d`](https://github.com/code-corhuila/barber-saas-docs/commit/7e7930d) | 2026-11-11 | docs(security): complete STRIDE and risk ranking for worker internal relay |
| [`378111a`](https://github.com/code-corhuila/barber-saas-docs/commit/378111a) | 2026-11-11 | docs(security): address review on STRIDE for worker internal relay |

Written by me on the branch of DOCS [#154](https://github.com/code-corhuila/barber-saas-docs/pull/154) (opened 2026-10-29) and rebased onto `main` by Daniel Cerquera when he merged it on 2026-11-11.

#### Every pull request I opened

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

#### Every issue I closed

| Date | Issue | How |
|---|---|---|
| 2026-11-10 | [docs#158](https://github.com/code-corhuila/barber-saas-docs/issues/158) | Closed as fixed right after the evidence comment |
| 2026-11-10 | [docs#159](https://github.com/code-corhuila/barber-saas-docs/issues/159) | Closed as fixed right after the evidence comment |
| 2026-11-10 | [docs#160](https://github.com/code-corhuila/barber-saas-docs/issues/160) | Closed as completed: criteria 1 and 3 fixed, 2 and 4 downgraded to P3 |

[docs#149](https://github.com/code-corhuila/barber-saas-docs/issues/149), my threat model claim, was closed by Daniel Cerquera on 2026-11-11 when #154 was merged.

#### Every tag and GitHub Release I published (2026-11-11)

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

#### Every comment and review of mine

| Date | Where |
|---|---|
| 2026-11-10 | [docs#158](https://github.com/code-corhuila/barber-saas-docs/issues/158#issuecomment-6778020315), [docs#159](https://github.com/code-corhuila/barber-saas-docs/issues/159#issuecomment-6778021787), [docs#160](https://github.com/code-corhuila/barber-saas-docs/issues/160#issuecomment-6778023155): evidence per acceptance criterion, then closed |
| 2026-11-10 | [appointment-api #34](https://github.com/code-corhuila/barber-saas-appointment-api/pull/34#issuecomment-6777925004): the security commits added to the release, for the teacher's review |
| 2026-11-11 | [appointment-api #34](https://github.com/code-corhuila/barber-saas-appointment-api/pull/34#issuecomment-6809794857): the CHANGELOG commit added, with a request for a new approval |
| 2026-11-11 | [loyalty-api #27](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/27#issuecomment-6809796948): the `[3.0.0]` CHANGELOG in the team's format (develop #28 → qa #29 → release.3.0.0 #30) |
| — | Reviews: none submitted by me since 2026-11-07. My threat model DOCS #154 was approved by the teacher (ariel5253) on 2026-11-10 and merged by Daniel Cerquera on 2026-11-11 |

#### Every commit of mine in this repository since 2026-11-07

| Commit | Date | Subject |
|---|---|---|
| [`e449980`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e449980298e78a2bb5ae3f6e7b2e49b280829d03) | 2026-11-07 | docs(certification): add chapter 7 evidence and 3 study hours |
| [`f4d520b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f4d520b76a4b8899f28f112401b500e8b620454d) | 2026-11-08 | docs(certification): add chapter 8 evidence and 3 study hours |
| [`a166097`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a166097b749d60ad8381c482aa2d580c1c3cf86f) | 2026-11-08 | docs(certification): add chapter 9 evidence and 3 study hours |
| [`e6d41b7`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/e6d41b77a765f92c9dc2879f14fdfffce1a84eaf) | 2026-11-08 | docs(certification): add chapter 10 evidence and 4 study hours |
| [`2ce23fe`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/2ce23fe92aab4c22fdd17abab178fe5fec3b4ba4) | 2026-11-08 | 📚 docs(week-14): complete hu-status with the MVP 3 promotion to qa |
| [`a9fbcdd`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a9fbcdd10c2141b49ef7f6dd2f27ff6d579d4e99) | 2026-11-08 | 🐛 fix(certification): log chapter 9 as 4 study hours |
| [`2eb6fd4`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/2eb6fd4254f4153b760b6766eb9fe511d2766de4) | 2026-11-08 | docs(certification): add chapter 11 evidence and 3 study hours |
| [`a9ac5e8`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/a9ac5e82d9bec44cadcf76d8434163c050dde538) | 2026-11-10 | docs(certification): add chapter 12 evidence and 2 study hours |
| [`197da56`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/197da564ccd2eda7b150617d1f4de330dedf3745) | 2026-11-10 | docs(certification): add chapter 13 evidence and 3 study hours |
| [`148040b`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/148040bfccf762c55ad526ff462a06f9c670954a) | 2026-11-13 | docs(certification): add chapter 14, four labs, final exam and certificate |
| [`f0a0e57`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/f0a0e573a49219f6ccbc55d124fc83750cd7f4d1) | 2026-11-13 | docs(certification): fill labs, skills summary, design note and ADRs |
| [`b8609fb`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/b8609fb23edf70ad4602f5c91d7409ea39147692) | 2026-11-13 | 📚 docs(week-15): complete hu-status with release 3.0.0, the worker token fixes and the HCCDP certificate |

#### Work outside GitHub

| Date | Work | Evidence |
|---|---|---|
| 2026-11-10 | `mvn -B verify` of loyalty-api and appointment-api before each merge (JDBC tests skipped without a database) | Output pasted in [#20](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/20), [#21](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/21), [#22](https://github.com/code-corhuila/barber-saas-loyalty-api/pull/22), [#35](https://github.com/code-corhuila/barber-saas-appointment-api/pull/35), [#36](https://github.com/code-corhuila/barber-saas-appointment-api/pull/36) |
| 2026-11-10 to 2026-11-13 | Huawei HCCDP course: chapters 12–14, four labs, final exam (19 hours this week) | [`certification/evidence/`](../../certification/evidence/) |
| 2026-11-12 | Final presentation of the team with the live demo on the Azure host | [Daniel Cerquera's week 15 report](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/15-week/hu-status/README.md) (HU-000-096) |
<!-- FULL-EVIDENCE-END -->
