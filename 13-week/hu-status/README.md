<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       13-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 13

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Close DOCS #114 by merging its seven UX/UI pull requests, so section 12 of DOCS shows the real `v2.0.0` app on `main`, and start the deployment stories I own (DEP-03 readiness in `barber-saas-loyalty-api`), with the team's correlation-id fix of the worker checked against my outbox producers.
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| DOCS-114 | As the team, we want section 12 (UX/UI) to show every screen, role and flow of the `v2.0.0` app, so the documentation matches what the code renders | done - merged 2026-10-26, issue closed | Issue [#114](https://github.com/code-corhuila/barber-saas-docs/issues/114) (closed as completed) · [#115](https://github.com/code-corhuila/barber-saas-docs/pull/115) [`349eb70`](https://github.com/code-corhuila/barber-saas-docs/commit/349eb70) · [#116](https://github.com/code-corhuila/barber-saas-docs/pull/116) [`256e0a4`](https://github.com/code-corhuila/barber-saas-docs/commit/256e0a4) · [#117](https://github.com/code-corhuila/barber-saas-docs/pull/117) [`52a9459`](https://github.com/code-corhuila/barber-saas-docs/commit/52a9459) · [#118](https://github.com/code-corhuila/barber-saas-docs/pull/118) [`cbb625b`](https://github.com/code-corhuila/barber-saas-docs/commit/cbb625b) · [#119](https://github.com/code-corhuila/barber-saas-docs/pull/119) [`f4146b7`](https://github.com/code-corhuila/barber-saas-docs/commit/f4146b7) · [#120](https://github.com/code-corhuila/barber-saas-docs/pull/120) [`12977fc`](https://github.com/code-corhuila/barber-saas-docs/commit/12977fc) · [#121](https://github.com/code-corhuila/barber-saas-docs/pull/121) [`6308780`](https://github.com/code-corhuila/barber-saas-docs/commit/6308780) |
| OBS-04 (team) | As an operator, I want the original correlation id to survive the outbox, so a booking and the notice it caused share one id | doing - fix merged in `develop` by Daniel Cerquera; end-to-end check and promotion pending | worker [#15](https://github.com/code-corhuila/barber-saas-worker/pull/15) · [`9f8daaa`](https://github.com/code-corhuila/barber-saas-worker/commit/9f8daaa) · my producers: appointment-api [`InternalOutboxHttpTest`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/develop/appointment-app/src/test/java/co/edu/corhuila/barbersaas/appointment/app/InternalOutboxHttpTest.java), loyalty-api [`InternalOutboxHttpTest`](https://github.com/code-corhuila/barber-saas-loyalty-api/blob/develop/loyalty-app/src/test/java/co/edu/corhuila/barbersaas/loyalty/app/InternalOutboxHttpTest.java) |
| DEP-03 (my part) | As the operator, I want a readiness check in loyalty, appointment and finance-inventory, so a deploy only sends traffic to a container that can serve | todo | Plan in [`deployment-plan.md`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/12-week/hu-status/deployment-plan.md) §5 and §7; starting point in [week 12](../../12-week/hu-status/README.md) §2 |
| AI-2 (week 11) | As a barber, I want a timeout of appointment-api to answer `503` with `Retry-After`, so the app can tell "try again" from "broken" | todo (unchanged) | Acceptance criteria in [`08-incident.md`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/blob/main/11-week/workshop/08-incident.md) |

## 2. My individual contribution
- **Closed DOCS #114 (2026-10-26).** I asked @ariel5253 to review the seven pull requests, and then,
  with the professor's permission to merge DOCS without his review (branch protection is no longer
  active on the private repository), merged #115 to #121 in order into `main`. Each one is a squash
  commit with the pull request title and `Refs: #114`, as the rest of `main`. The merges brought
  1,795 added and 495 deleted lines in 163 files: `main` now has `12-ux-ui/mockups/` with 119 PNG
  screens and boards, the rewritten `design-system.md`, `navigation-map.md` and `wireframes.md`, and
  the prototype mockup in `99-archive/deprecated/`; no document links `mockup-auth-onboarding.png`
  any more. I closed the issue with a comment that lists the seven pull requests.
- **Checked the worker fix against my producers (OBS-04).** `appointment-api` and `loyalty-api`
  store the request's `X-Correlation-Id` in each outbox row (`corr-book`, `corr-sticker` in their
  `InternalOutboxHttpTest`), but the worker delivered the events with the id of its own run.
  Daniel's [#15](https://github.com/code-corhuila/barber-saas-worker/pull/15) sends the envelope's
  `correlationId` instead. Its CI never started (the organization's Actions are locked for billing),
  so I ran the tests locally on 2026-10-26 on the exact commits: with the new tests on the code
  before the fix, `1 failed, 9 passed` (`assert 'run-123' == 'corr-test'`); on `9f8daaa`,
  `24 passed` and `lint-imports` with 3 contracts kept.

## 3. Blockers and risks
- **GitHub Actions is locked for the organization** (billing): the worker's CI of #15 did not run,
  and pull requests can be merged with no green check. Until it is fixed, every pull request needs
  its local test output in the description.
- **No review on `main`.** Branch protection does not apply to the private repositories on the
  current plan, so DOCS #115 to #121 were merged without a reviewer; mistakes have to be caught by
  the author.
- **OBS-04 is only partly done**: the worker still logs its run id under the name `correlationId`
  (the story asked for `runId`), and step 3 of runbook RB-01 still tells to search the run's id in
  the consumers' logs, which no longer receive it.
- **Still open**: DEP-01 and DEP-03 in my three APIs, AI-1 to AI-4 of week 11, the flaky
  `InMemoryLoyaltyRepositoryTest`, and my share of [DOCS #111](https://github.com/code-corhuila/barber-saas-docs/issues/111).
- **Certification C3**: the course template (`certification/`) arrived in `code-corhuila` on
  2026-10-26 and is not yet synced into this fork.

## 4. Plan for next week
- DEP-03 in `barber-saas-loyalty-api`: `GET /health/ready` with a database check, test first
  (PostgreSQL stopped -> `503`, `/health` still `200`), one pull request to `develop` with its local
  test output; then appointment and finance-inventory.
- AI-2 in `barber-saas-loyalty-api`: a dependency timeout answers `503` with `Retry-After`.
- Run the end-to-end check of OBS-04 with Daniel (book with `X-Correlation-Id: corr-test`, find it
  in the `notifications-api` log) and fix step 3 of RB-01.
- Sync this fork with `code-corhuila` and fill the corte 3 certification.

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary`: the seven squash commits on DOCS `main` are
      `docs(ux-ui): ...`.
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...): DOCS uses
      `docs/NNN` branches with PRs to `main`; no code repository of mine changed yet this week.
- [x] Testable acceptance criteria: DEP-03 (`/health/ready` -> 503 with PostgreSQL stopped) and AI-2
      are written as checks; OBS-04 has its end-to-end check listed in section 4.
- [ ] Tests added/updated (unit / integration): none of mine this week; I only ran the worker's
      tests of #15 locally.
- [x] DDD / hexagonal boundaries respected (domain has no I/O): the worker fix stays in its HTTP
      adapter (`lint-imports`: 3 contracts kept); DEP-03 is planned as an inbound HTTP adapter.
- [x] No secrets; config via environment variables: the mockups use no real data; nothing with
      credentials was changed.

## 6. Evidence links
- UX/UI issue (closed): https://github.com/code-corhuila/barber-saas-docs/issues/114
- Merged pull requests: https://github.com/code-corhuila/barber-saas-docs/pull/115 · https://github.com/code-corhuila/barber-saas-docs/pull/116 · https://github.com/code-corhuila/barber-saas-docs/pull/117 · https://github.com/code-corhuila/barber-saas-docs/pull/118 · https://github.com/code-corhuila/barber-saas-docs/pull/119 · https://github.com/code-corhuila/barber-saas-docs/pull/120 · https://github.com/code-corhuila/barber-saas-docs/pull/121
- Section 12 on `main`: https://github.com/code-corhuila/barber-saas-docs/tree/main/12-ux-ui
- Worker fix: https://github.com/code-corhuila/barber-saas-worker/pull/15
- **Record of the week so far** (from 2026-10-26):
  - `barber-saas-docs`: 7 pull requests merged (#115 to #121, opened in week 12), 7 squash commits
    on `main`, 1 issue closed (#114), 7 review requests.
  - Code repositories of `code-corhuila`: 0 commits, 0 pull requests of mine.
  - This fork: this file.
