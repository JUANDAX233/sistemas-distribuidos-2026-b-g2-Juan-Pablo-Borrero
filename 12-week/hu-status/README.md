<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       12-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 12

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Replace section 12 (UX/UI) of DOCS, which still showed the first Expo prototype, with high-fidelity mockups of every screen of the `v2.0.0` app (DOCS #114, 7 pull requests of 400 lines or fewer), and check the team's MVP 3 deployment plan against my three services - appointment, loyalty and finance-inventory - so the deployment stories I own start from what their `deploy/` files really do.
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| DOCS-114 | As the team, we want section 12 (UX/UI) to show every screen, role and flow of the `v2.0.0` app, so the documentation matches what the code renders | doing - 7 pull requests open, waiting for review (not merged) | Issue [DOCS #114](https://github.com/code-corhuila/barber-saas-docs/issues/114) · PRs [#115](https://github.com/code-corhuila/barber-saas-docs/pull/115) · [#116](https://github.com/code-corhuila/barber-saas-docs/pull/116) · [#117](https://github.com/code-corhuila/barber-saas-docs/pull/117) · [#118](https://github.com/code-corhuila/barber-saas-docs/pull/118) · [#119](https://github.com/code-corhuila/barber-saas-docs/pull/119) · [#120](https://github.com/code-corhuila/barber-saas-docs/pull/120) · [#121](https://github.com/code-corhuila/barber-saas-docs/pull/121) |
| W11-WS-1 / W11-WS-2 | Week 11 observability workshop for the sticker journey (CP0-CP10) | done - 2026-10-19 | Reported in [week 11](../../11-week/hu-status/README.md); not counted again here |
| DEP-PLAN (team) | As the team, we want the MVP 3 deployment plan: digest promotion, IaC, rolling update, rollback and the DEP stories | done (written by Daniel Cerquera) | [`deployment-plan.md`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/12-week/hu-status/deployment-plan.md) · [`2bcb5b0`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/commit/2bcb5b0) · [`9ffe652`](https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/commit/9ffe652) |
| DEP-01 / DEP-03 (my part) | As the operator, I want appointment, loyalty and finance-inventory published as tagged images and with a readiness check, so a deploy only sends traffic to a container that can serve | todo | Starting point read on `v2.0.0`: [`appointment-api/deploy/compose.yml`](https://github.com/code-corhuila/barber-saas-appointment-api/blob/v2.0.0/deploy/compose.yml) · [`loyalty-api/deploy/compose.yml`](https://github.com/code-corhuila/barber-saas-loyalty-api/blob/v2.0.0/deploy/compose.yml) · [`finance-inventory-api/deploy/compose.yml`](https://github.com/code-corhuila/barber-saas-finance-inventory-api/blob/v2.0.0/deploy/compose.yml) |
| OBS-02 (team) | Rate, error and latency metrics on every HTTP service (my part: my three APIs) | todo (unchanged) | Week 11 [`StickerMetrics`](https://github.com/JUANDAX233/sistemas-distribuidos-2026-b-g2-Juan-Pablo-Borrero/commit/42e86d2) is still the prototype |

## 2. My individual contribution
- **Rewrote DOCS section 12 (UX/UI) from the code** (issue [#114](https://github.com/code-corhuila/barber-saas-docs/issues/114),
  22 commits in 7 branches `docs/075` to `docs/081`, each PR to `main` with `Refs: #114`):
  - [#115](https://github.com/code-corhuila/barber-saas-docs/pull/115) design system: the real tokens of
    `barber-saas-front/src/theme.css` instead of colors guessed from an image, plus `kit.css` and the
    17 Ionicons the app uses (+314/-204).
  - [#116](https://github.com/code-corhuila/barber-saas-docs/pull/116) navigation map with the real routes
    and the HTML-to-PNG renderer of the mockups (+312/-171).
  - [#117](https://github.com/code-corhuila/barber-saas-docs/pull/117) to [#120](https://github.com/code-corhuila/barber-saas-docs/pull/120)
    mockups (390x844, Spanish texts of the code) of the shell, identity-auth, barbershop, appointment,
    schedule, finance-inventory, loyalty, notifications and platform-admin apps, with flow boards and
    the loading / error / empty states of norm 5.4.d.
  - [#121](https://github.com/code-corhuila/barber-saas-docs/pull/121) `wireframes.md` with every screen,
    its route and its HU/FR, a "Not implemented in v2.0.0" list, and the prototype mockup moved to
    `99-archive/deprecated/` (renamed, not deleted), which also fixes the broken `.png` links.
- **Checked the team's deployment plan against my three services** (read only, tag `v2.0.0`):
  - All three build the image on the host (`build:` in `deploy/compose.yml`), so DEP-01 and DEP-02
    apply to them as written.
  - The health check is `GET /health` every 5 s, 30 retries, through `/dev/tcp`; the
    `HealthController` of each one does not touch PostgreSQL, so DEP-03 (readiness) is needed here too.
  - The Dockerfile copies `*-app-0.1.0.jar` although the git tag is `v2.0.0`: the image tag of DEP-01
    must come from the git tag, not from the Maven version.
  - Each one has a 512 MB memory limit and `DB_POOL_MAX=10`: with `--scale 2` in the rolling update,
    one service briefly uses 1 GB and 20 PostgreSQL connections.
  - `loyalty-api` and `finance-inventory-api` call `appointment-api`, and `appointment-api` calls
    `loyalty-api` (coupon at booking, DEC-APPT-09), so the old and the new version of each API must
    accept the other's calls during a deploy, the same rule the plan sets for the schema.

## 3. Blockers and risks
- **DOCS #114 is not merged**: the 7 PRs wait for review; the images of #121 only resolve after
  #117 to #120 are merged. Until then the section is not done.
- **No image registry and no host**: none of my services can be deployed from a tagged image or
  rolled back yet (plan §8).
- **Liveness only**: my three APIs report `healthy` with PostgreSQL down.
- **Two-way call between appointment-api and loyalty-api**: a deploy of one with a changed contract
  breaks the other while both versions run; it needs a contract test before DEP-04.
- **No PR in a code repository this week**; still open from weeks 10 and 11: the flaky
  `InMemoryLoyaltyRepositoryTest`, the 3 coupon branches outside `develop`, AI-1 to AI-4 of the
  week 11 incident and my share of [DOCS #111](https://github.com/code-corhuila/barber-saas-docs/issues/111).

## 4. Plan for next week
- Answer the review of DOCS #115 to #121 until they are merged, and close #114.
- DEP-03 in `barber-saas-loyalty-api` first: `GET /health/ready` with a database check, test first
  (PostgreSQL stopped -> 503), one PR to `develop`, then the same in appointment and finance-inventory.
- DEP-01 for my three APIs after the pilot in `appointment-api` agreed with the team: build once on
  the `vX.Y.Z` tag and push to GHCR with the git tag as image tag.
- Open the observability issue in DOCS with AI-1 to AI-4 of week 11 and start AI-2 (`503` on a
  dependency timeout in loyalty-api).

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary`: the 22 commits of #114 use `docs(ux-ui): ...`.
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...): DOCS works
      with `docs/NNN` branches and PRs to `main`, which is its flow; no code repository changed this week.
- [x] Testable acceptance criteria: DEP-03 is checked by stopping PostgreSQL (`/health/ready` -> 503,
      `/health` -> 200); #121 can be checked by every screen of `wireframes.md` linking an existing PNG.
- [ ] Tests added/updated (unit / integration): none this week; the DOCS PRs are documentation.
- [x] DDD / hexagonal boundaries respected (domain has no I/O): the readiness check is planned as an
      inbound HTTP adapter next to `HealthController`; no `-core` module changes.
- [x] No secrets; config via environment variables: the passwords and the JWT key of my services stay
      as `${...:?}` variables in `deploy/compose.yml`; the mockups use no real data.

## 6. Evidence links
- UX/UI issue: https://github.com/code-corhuila/barber-saas-docs/issues/114
- UX/UI pull requests: https://github.com/code-corhuila/barber-saas-docs/pull/115 · https://github.com/code-corhuila/barber-saas-docs/pull/116 · https://github.com/code-corhuila/barber-saas-docs/pull/117 · https://github.com/code-corhuila/barber-saas-docs/pull/118 · https://github.com/code-corhuila/barber-saas-docs/pull/119 · https://github.com/code-corhuila/barber-saas-docs/pull/120 · https://github.com/code-corhuila/barber-saas-docs/pull/121
- Team deployment plan: https://github.com/Pipecerquera/sistemas-distribuidos-2026-b-g2-daniel-cerquera/blob/main/12-week/hu-status/deployment-plan.md
- Deploy files of my services at `v2.0.0`: https://github.com/code-corhuila/barber-saas-appointment-api/blob/v2.0.0/deploy/Dockerfile · https://github.com/code-corhuila/barber-saas-loyalty-api/blob/v2.0.0/deploy/Dockerfile · https://github.com/code-corhuila/barber-saas-finance-inventory-api/blob/v2.0.0/deploy/Dockerfile
- **Record of the week** (2026-10-19 to 2026-10-25):
  - `barber-saas-docs`: 1 issue (#114), 7 pull requests (#115 to #121, open), 22 commits on 2026-10-19.
  - Code repositories of `code-corhuila`: 0 commits, 0 pull requests, 0 reviews.
  - This fork: the week 11 workshop commits of 2026-10-19 (reported in week 11) and this file.
