<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       07-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 07

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Index the BarberSaaS ecosystem's related repositories from the docs source of truth, so the product code repo (`barber-saas`) and the individual weekly-grading repo are discoverable from a clone of `barber-saas-docs` alone.
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| GOV-ARCHIVE-01 | Add a related-repositories index to `barber-saas-docs` pointing to the `barber-saas` (product) and weekly-grading repos | done | `barber-saas-docs` commit `5b9af66` on branch `docs/005-archive-related-repos` |

## 2. My individual contribution
- Added `99-archive/related-repositories.md` to `barber-saas-docs`, documenting the three repos of the ecosystem (docs, product code, individual weekly deliverable), their scope, and their remotes.
- Noted there that `barber-saas`'s real backend/mobile code lives on its `develop` branch, not `main` — `main` there has no promoted release yet — so anyone reading the docs repo alone knows where to actually look for the code.
- Clarified that this weekly repo is graded individually per `00-governance/agile-conventions.md` and is not governed by `barber-saas-docs`'s own conventions.
- Updated `99-archive/README.md` to link the new index.

## 3. Blockers and risks
- The commit landed on branch `docs/005-archive-related-repos`, not yet merged into `main` — the PR is still open as of this report.
- Most of this week's real, evidenced work happened in `barber-saas-docs`; there were no commits this week in the `barber-saas` product repo or in this weekly repo itself under my account. Flagging this honestly instead of padding the table with unrelated work from teammates.
- `barber-saas`'s `main` branch still has no promoted release — its README says to check out `develop` to see the actual backend/mobile code, which this weekly report can't independently verify was updated this week (no commits in that window on `develop` either).

## 4. Plan for next week
- Get the `docs/005-archive-related-repos` PR reviewed and merged to `main`.
- Pick up the next open item in the ecosystem backlog (`_ecosistema/SPEC-PLAN-PROMPT.md` §7): reconciling ADR-002 (modular monolith) with the `09-microservices` section, or documenting the real multi-tenant model in `05-architecture`/`06-data` — whichever gets prioritized.

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary` (`docs(archive): index related repos ...`)
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...) — branch follows the repo's own `docs/NNN-slug` convention instead; PR still open, not merged
- [ ] Testable acceptance criteria — this was a documentation pointer, not a scoped HU with formal AC
- [ ] Tests added/updated (unit / integration) — not applicable, docs-only change, no code touched
- [ ] DDD / hexagonal boundaries respected (domain has no I/O) — not applicable, no code touched this week
- [x] No secrets; config via environment variables

## 6. Evidence links
- https://github.com/code-corhuila/barber-saas-docs/commit/5b9af6650511a3b25e11178438db45fd8e8dfbf3
- https://github.com/code-corhuila/barber-saas-docs/tree/docs/005-archive-related-repos
