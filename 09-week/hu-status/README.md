<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       09-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 09

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Close the reviewer's red items in section 07-api by publishing an OpenAPI 3.1 contract for every domain service (8 of 8), aligned with the course norm 2026-B, before the week-10 checkpoint.
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| DOC-API-09-A | OpenAPI contracts for barbershop, schedule, loyalty, finance-inventory and platform-admin services | done | https://github.com/code-corhuila/barber-saas-docs/pull/35 |
| DOC-API-09-B | Index the eight service contracts in `07-api/README.md` and record open contract decisions (OQ-06…OQ-11) | done | https://github.com/code-corhuila/barber-saas-docs/commit/839bb1e |
| GOV-AGILE-08-A | SPEC-008: WIP limit, story map, MVP2 backlog, sprint-status (carried over from week 08) | doing | https://github.com/code-corhuila/barber-saas-docs/issues/51 · PRs [#52](https://github.com/code-corhuila/barber-saas-docs/pull/52), [#53](https://github.com/code-corhuila/barber-saas-docs/pull/53), [#54](https://github.com/code-corhuila/barber-saas-docs/pull/54) (open, waiting for review) |
| GOV-AGILE-08-B | SPEC-009: GitHub Project board | todo | Unblocked (`gh` now authenticated), not started |

## 2. My individual contribution
- Wrote the five missing OpenAPI 3.1 contracts in `07-api/contracts/openapi/`:
  `barbershop-service.yaml` (566aed8), `schedule-service.yaml` (c7dfc5b),
  `loyalty-service.yaml` (d29be4f), `finance-inventory-service.yaml` (0915077) and
  `platform-admin-service.yaml` (babb39a).
- Each contract follows the norm: one service per file, `/api/v1/...` paths served through
  the api-gateway, UUID ids and `*Cents` money from `_shared.yaml`, and `barbershopId` taken
  from the token only. The one exception is the public discovery catalog, documented as
  `DEC-SHOP-02` / OQ-07. Each contract traces back to FR ids in `04-requirements` and to
  tables in `06-data/models.md`.
- Indexed all 8 service contracts in `07-api/README.md` and added OQ-06…OQ-11 to
  `07-api/open-questions.md` (839bb1e).
- PR #35: 6 commits, 7 files, +4087 lines. Approved by the reviewer (`ariel5253`) and
  merged on 2026-09-28.
- Opened the SPEC-008 carry-over for review (2026-10-01): created issue #51 and split the
  683-line commit `6d7436f` into three PRs from current `main` with `cherry-pick -x` (#52 WIP
  limit + sprint status, #53 sprint-ready stories, #54 story map + MVP2 backlog), leaving out
  the unrelated commit `5b9af66` and without rewriting the published branch.
- Filled week 09 Session 1 (sprint) and Session 2 (planning) in this repo.

## 3. Blockers and risks
- The 5 `-api` repos still contain only `README.md` + `CODEOWNERS`, so the contracts were
  derived from the monolith's controllers/DTOs (`barber-saas@develop`). They can drift once
  the real services are built.
- SPEC-008 is not merged yet: PRs #52–#54 were opened on 2026-10-01 and wait for
  `ariel5253`. #53 has 467 changed lines, above the 400-line limit (norm 9.2); the reason is
  in the PR. #54 depends on #53, so the merge order matters.
- Story ID conflict: issues #21–#24 use different IDs for part of the same scope as #53 (for
  example #24 `HU-SADMIN-002` = `HU-SADMIN-001-B`). The team has to choose one scheme.
- The team WIP limit (In Review ≤ 2) was not respected: 7 PRs (#36–#42) were in review at the
  same time on 2026-09-29 and 5 (#44–#48) on 2026-09-30.
- No live planning poker with the team this week, so next week's estimates are individual
  and provisional.
- Known risks recorded in DOCS: AT-008 (all 29 code repo READMEs say "LMS Library"),
  AT-006 (the service catalog still describes the monolith) and the conflicting plan names.

## 4. Plan for next week
- Get SPEC-008 PRs #52–#54 reviewed and merged in order (#52 → #53 → #54) and settle the
  story-ID scheme with the team (3 SP).
- Close OQ-01 (`429` in `auth-service.yaml`, 1 SP) and work on OQ-06…OQ-11 (5 SP).
- Create the GitHub Project board (SPEC-009, 2 SP).
- Run a real planning poker with the team to confirm the estimates in `02-session`.

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary` (`docs(api): add ... OpenAPI`, all 6 commits in #35)
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...) - DOCS uses `docs/<slug>` branches with PRs to `main`; no environment branches apply to documentation
- [x] Testable acceptance criteria - each contract is checkable: OpenAPI 3.1, `/api/v1` paths, `_shared.yaml` refs, FR coverage table in PR #35
- [ ] Tests added/updated (unit / integration) - not applicable, no code changed this week
- [ ] DDD / hexagonal boundaries respected (domain has no I/O) - not applicable to code this week; contracts follow one bounded context per service (ADR-004)
- [x] No secrets; config via environment variables - no secrets in any contract or doc

## 6. Evidence links
- https://github.com/code-corhuila/barber-saas-docs/pull/35
- https://github.com/code-corhuila/barber-saas-docs/tree/main/07-api/contracts/openapi
- https://github.com/code-corhuila/barber-saas-docs/blob/main/07-api/open-questions.md
- https://github.com/code-corhuila/barber-saas-docs/issues/51
- https://github.com/code-corhuila/barber-saas-docs/pull/52 · https://github.com/code-corhuila/barber-saas-docs/pull/53 · https://github.com/code-corhuila/barber-saas-docs/pull/54
- [Session 1 (sprint)](../01-session/README.md) · [Session 2 (planning)](../02-session/README.md)
