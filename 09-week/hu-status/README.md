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
| HU-APPT-001 | Book an appointment without double-booking — `appointment-db` | done | appointment-db [#2](https://github.com/code-corhuila/barber-saas-appointment-db/pull/2), [#3](https://github.com/code-corhuila/barber-saas-appointment-db/pull/3), [#4](https://github.com/code-corhuila/barber-saas-appointment-db/pull/4) |
| HU-APPT-001 | Book an appointment without double-booking — `appointment-api` | doing | appointment-api [#2](https://github.com/code-corhuila/barber-saas-appointment-api/pull/2), [#3](https://github.com/code-corhuila/barber-saas-appointment-api/pull/3), [#4](https://github.com/code-corhuila/barber-saas-appointment-api/pull/4), [#5](https://github.com/code-corhuila/barber-saas-appointment-api/pull/5), [#6](https://github.com/code-corhuila/barber-saas-appointment-api/pull/6), [#7](https://github.com/code-corhuila/barber-saas-appointment-api/pull/7), [#8](https://github.com/code-corhuila/barber-saas-appointment-api/pull/8), [#9](https://github.com/code-corhuila/barber-saas-appointment-api/pull/9), [#10](https://github.com/code-corhuila/barber-saas-appointment-api/pull/10), [#11](https://github.com/code-corhuila/barber-saas-appointment-api/pull/11) (finished in week 10) |
| HU-APPT-001 | Book an appointment without double-booking — `appointment-app` | doing | appointment-app [#2](https://github.com/code-corhuila/barber-saas-appointment-app/pull/2), [#3](https://github.com/code-corhuila/barber-saas-appointment-app/pull/3), [#4](https://github.com/code-corhuila/barber-saas-appointment-app/pull/4), [#5](https://github.com/code-corhuila/barber-saas-appointment-app/pull/5), [#6](https://github.com/code-corhuila/barber-saas-appointment-app/pull/6) (finished in week 10) |
| GOV-SHARED-09 | Plug the appointment domain into the shared platform (gateway routes, infra include) | done | api-gateway [#6](https://github.com/code-corhuila/barber-saas-api-gateway/pull/6) · infra-postgres [#8](https://github.com/code-corhuila/barber-saas-infra-postgres/pull/8) |

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
- **Started the appointment domain as microservices (2026-10-04, after the first version of
  this report):** 20 pull requests into `develop`, all merged with green CI, +10,189 lines.
  - `appointment-db` (#2–#4): Liquibase structure and the `appointment-db-migrate` runner, the
    `appointment` schema with the no-double-booking `EXCLUDE USING gist` constraint, and the
    reader/writer grants.
  - `appointment-api` (#2–#11): Java 21 / Spring Boot 3.5 in three Maven modules — the
    aggregate and its state machine, the ports, the use cases, JDBC and in-memory
    repositories, the clients of barbershop-api and schedule-api, the error envelope and token
    validation, the composition root, the endpoints and the deploy files. `appointment-core`
    has no Spring dependency; 9 test classes.
  - `appointment-app` (#2–#6): the Ionic React remote — typed calls through the shell's client,
    the client's booking and appointments, the barber and owner agendas, the barber history,
    with Vitest specs.
  - Shared pieces, only my lines: the appointment routes in `barber-saas-api-gateway` (#6) and
    the `include` line in `barber-saas-infra-postgres` (#8).
- **24 pull requests this week** (4 in DOCS, 20 in code) and **104 commits** (96 in the three
  appointment repos, 4 in DOCS, 2 in the shared repos, 2 in this repo).

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
- [x] Conventional Commits - `type(scope): summary` (`docs(api): add ... OpenAPI`, all 6 commits in #35; `feat(domain): add the appointment aggregate and its state machine`, appointment-api #3)
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...) - the 20 code PRs went from `feat/` and `chore/` branches into `develop`; nothing was promoted to `qa` this week. DOCS uses `docs/<slug>` branches with PRs to `main`
- [x] Testable acceptance criteria - each contract is checkable: OpenAPI 3.1, `/api/v1` paths, `_shared.yaml` refs, FR coverage table in PR #35; each code PR lists its checks
- [x] Tests added/updated (unit / integration) - 9 test classes in appointment-api (domain, use cases with fakes, repositories, HTTP), Vitest specs in appointment-app; CI green on the 20 PRs
- [x] DDD / hexagonal boundaries respected (domain has no I/O) - `appointment-core` has no Spring dependency; adapters and the composition root are separate Maven modules; contracts follow one bounded context per service (ADR-004)
- [x] No secrets; config via environment variables - no secrets in any contract or doc

## 6. Evidence links
- https://github.com/code-corhuila/barber-saas-docs/pull/35
- https://github.com/code-corhuila/barber-saas-docs/tree/main/07-api/contracts/openapi
- https://github.com/code-corhuila/barber-saas-docs/blob/main/07-api/open-questions.md
- https://github.com/code-corhuila/barber-saas-docs/issues/51
- https://github.com/code-corhuila/barber-saas-docs/pull/52 · https://github.com/code-corhuila/barber-saas-docs/pull/53 · https://github.com/code-corhuila/barber-saas-docs/pull/54
- [Session 1 (sprint)](../01-session/README.md) · [Session 2 (planning)](../02-session/README.md)

### Every pull request I opened this week (24)

| Repository | PR | Title | Into | State |
|---|---|---|---|---|
| barber-saas-api-gateway | [#6](https://github.com/code-corhuila/barber-saas-api-gateway/pull/6) | feat(routes): route the appointment domain | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#2](https://github.com/code-corhuila/barber-saas-appointment-api/pull/2) | chore(build): add the maven build and the ci pipeline | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#3](https://github.com/code-corhuila/barber-saas-appointment-api/pull/3) | feat(domain): add the appointment aggregate and its state machine | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#4](https://github.com/code-corhuila/barber-saas-appointment-api/pull/4) | feat(application): declare the ports of the appointment service | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#5](https://github.com/code-corhuila/barber-saas-appointment-api/pull/5) | feat(application): add the appointment use cases | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#6](https://github.com/code-corhuila/barber-saas-appointment-api/pull/6) | feat(persistence): add the jdbc and in-memory appointment repositories | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#7](https://github.com/code-corhuila/barber-saas-appointment-api/pull/7) | feat(http): add the clients of barbershop-api and schedule-api | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#8](https://github.com/code-corhuila/barber-saas-appointment-api/pull/8) | feat(http): add the error envelope, correlation, health and token validation | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#9](https://github.com/code-corhuila/barber-saas-appointment-api/pull/9) | feat(app): compose the service with its adapters and limits | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#10](https://github.com/code-corhuila/barber-saas-appointment-api/pull/10) | feat(http): expose the appointment endpoints | `develop` | merged 2026-10-04 |
| barber-saas-appointment-api | [#11](https://github.com/code-corhuila/barber-saas-appointment-api/pull/11) | feat(deploy): compose the appointment service and document it | `develop` | merged 2026-10-04 |
| barber-saas-appointment-app | [#2](https://github.com/code-corhuila/barber-saas-appointment-app/pull/2) | chore(app): add the ionic react remote, its build and the ci | `develop` | merged 2026-10-04 |
| barber-saas-appointment-app | [#3](https://github.com/code-corhuila/barber-saas-appointment-app/pull/3) | feat(appointment): add the typed api calls, the screen rules and the routes | `develop` | merged 2026-10-04 |
| barber-saas-appointment-app | [#4](https://github.com/code-corhuila/barber-saas-appointment-app/pull/4) | feat(ui): add the client's appointments and the booking screens | `develop` | merged 2026-10-04 |
| barber-saas-appointment-app | [#5](https://github.com/code-corhuila/barber-saas-appointment-app/pull/5) | feat(ui): add the barber and owner agendas and the barber history | `develop` | merged 2026-10-04 |
| barber-saas-appointment-app | [#6](https://github.com/code-corhuila/barber-saas-appointment-app/pull/6) | feat(deploy): serve the appointment remote and document the app | `develop` | merged 2026-10-04 |
| barber-saas-appointment-db | [#2](https://github.com/code-corhuila/barber-saas-appointment-db/pull/2) | chore(liquibase): add the liquibase structure and the migration runner | `develop` | merged 2026-10-04 |
| barber-saas-appointment-db | [#3](https://github.com/code-corhuila/barber-saas-appointment-db/pull/3) | feat(ddl): create the appointment schema | `develop` | merged 2026-10-04 |
| barber-saas-appointment-db | [#4](https://github.com/code-corhuila/barber-saas-appointment-db/pull/4) | feat(dcl): grant the appointment permissions | `develop` | merged 2026-10-04 |
| barber-saas-docs | [#35](https://github.com/code-corhuila/barber-saas-docs/pull/35) | docs(api): add the five missing domain OpenAPI contracts | `main` | merged 2026-09-28 |
| barber-saas-docs | [#52](https://github.com/code-corhuila/barber-saas-docs/pull/52) | docs(agile-process): define the wip limit and track sprint status | `main` | merged in week 10 |
| barber-saas-docs | [#53](https://github.com/code-corhuila/barber-saas-docs/pull/53) | docs(requirements): split the backlog into sprint-ready user stories | `main` | closed |
| barber-saas-docs | [#54](https://github.com/code-corhuila/barber-saas-docs/pull/54) | docs(product): add the story map and the mvp2 backlog | `main` | merged in week 10 |
| barber-saas-infra-postgres | [#8](https://github.com/code-corhuila/barber-saas-infra-postgres/pull/8) | feat(compose): include appointment in the platform | `develop` | merged 2026-10-04 |
