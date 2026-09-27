<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       08-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 08

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- SPRINT_GOAL: Close the agile-process compliance gap found against the course rubric ("Run your sprint like a pro" + "Session 2 planning"): define a WIP limit, split the epic-sized backlog into sprint-ready HUs, build a story map and an MVP2 backlog, and create the GitHub Project board.
<!-- CONFIG-END -->

## 1. User stories worked this week
| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| GOV-AGILE-08-A | SPEC-008: WIP limit, sprint tracking, HU split (10→18), retroactive sizing, story map, MVP2 backlog | doing | `barber-saas-docs`, branch `docs/008-agile-process-executable` (not pushed yet — pending commit authorization) |
| GOV-AGILE-08-B | SPEC-009: create GitHub Project board for `barber-saas` (CODE) | todo | Blocked — `gh auth status` has no active session |
| GOV-AGILE-08-C | SPEC-010: fill Session 1/Session 2/hu-status for the active week (this file) | doing | This weekly repo, branch `docs/010-week-08-session-evidence` (not pushed yet) |

## 2. My individual contribution
- Ran a compliance audit against the two-part course rubric fragment (sprint execution +
  Session 2 planning), read against the real state of DOCS, CODE, and this repo — not
  assumed from memory.
- Used `spec-forge` to produce SPEC-008/009/010, PLAN-008/009/010, and HANDOFF-008/009/010
  (`_ecosistema/specs/`, `_ecosistema/handoffs/`), then executed them directly.
- In DOCS: added a WIP limit to `00-governance/agile-conventions.md`, split the 10
  epic-sized HUs in `04-requirements/user-stories.md` into 18 sprint-ready HUs with
  retroactive Fibonacci sizing, updated `04-requirements/traceability-matrix.md`
  accordingly, and created `15-project-control/sprint-status.md`,
  `03-product/story-map.md`, and `03-product/mvp2-backlog.md`.
- In CODE: attempted to create the GitHub Project board — blocked on `gh` authentication,
  reported rather than worked around.
- In this repo: filled `08-week/01-session/README.md` (sprint) and `02-session/README.md`
  (planning) with evidence reconstructed from real `git log` output across the three repos,
  including the honest finding that there was no committed activity anywhere between
  2026-09-18 and 2026-09-26.

## 3. Blockers and risks
- **Nothing is committed or pushed yet.** All SPEC-008 and SPEC-010 changes exist only in
  local working trees, on local branches, pending Daniel's explicit authorization to
  `git commit`/`git push` (non-negotiable rule in every repo's `CLAUDE.md`). This week's
  "Evidence" column above points to branches/paths, not live GitHub URLs, because none
  exist yet.
- SPEC-009 (GitHub Project board) is fully blocked: `gh auth status` shows no active
  session in this environment.
- The sprint numbering used in `15-project-control/sprint-status.md` (Sprint 1 = week of
  2026-08-31) is an **inference** from commit dates, not a confirmed fact — needs Daniel's
  confirmation.
- No real planning-poker session with the team (Carlos, Juan Pablo, Carolay) happened this
  week — the MVP2 backlog estimates are explicitly left as "pending" rather than invented.

## 4. Plan for next week
- Get explicit authorization from Daniel to commit and push SPEC-008 (DOCS) and SPEC-010
  (this repo), open the corresponding PRs, and get them reviewed/merged.
- Resolve `gh auth login` and re-run SPEC-009 to actually create the GitHub Project board.
- Run a real, live planning-poker session with the team to replace the "pending" estimates
  in `03-product/mvp2-backlog.md` with real ones.
- Confirm or correct the inferred sprint numbering in `15-project-control/sprint-status.md`.

## 5. Compliance self-check
- [x] Conventional Commits - `type(scope): summary` (planned trailer: `Spec: SPEC-008` / `Spec: SPEC-010`, not committed yet)
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...) — DOCS/WEEKLY use their own `docs/NNN-slug` convention instead; no PR opened yet, nothing pushed
- [ ] Testable acceptance criteria — this week's work is process/governance documentation, not a scoped product HU with Given/When/Then AC
- [ ] Tests added/updated (unit / integration) — not applicable, no code touched this week
- [ ] DDD / hexagonal boundaries respected (domain has no I/O) — not applicable, no code touched this week
- [x] No secrets; config via environment variables — no secrets touched in any of the files edited

## 6. Evidence links
- `barber-saas-docs`, local branch `docs/008-agile-process-executable` (not pushed — no PR/commit URL exists yet)
- This repo, local branch `docs/010-week-08-session-evidence` (not pushed — no PR/commit URL exists yet)
- `_ecosistema/specs/SPEC-008-proceso-agil-ejecutable.md`, `SPEC-009-board-github-projects-code.md`, `SPEC-010-sesiones-semana-activa-weekly.md`
- `_ecosistema/handoffs/HANDOFF-008.md`, `HANDOFF-009.md`, `HANDOFF-010.md`
