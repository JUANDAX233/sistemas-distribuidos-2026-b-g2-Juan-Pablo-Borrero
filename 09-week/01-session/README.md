# Week 09 — Session 1 (Sprint)

> Filled 2026-10-01. Every entry below is backed by real, verifiable state (`git log`,
> merged PRs in `code-corhuila/barber-saas-docs`, file paths). No daily-sync entry was
> written for a day without evidence.

---

## Backlog priorizado de la semana

El foco del sprint fue cerrar los ítems marcados en rojo por el revisor del curso
y alinear DOCS con la Norma 2026-B antes del corte de la semana 10. No se tocó código
de producto: los 29 repos `barber-saas-*` siguen solo con `README.md` y `.github/CODEOWNERS`.

| Ítem | Sección | Responsable | Estado al cierre de la sesión |
|------|---------|-------------|-------------------------------|
| Contratos OpenAPI de los 5 dominios que faltaban | 07-api | Juan Pablo | ✅ Mergeado — [PR #35](https://github.com/code-corhuila/barber-saas-docs/pull/35) |
| Contrato común `_shared.yaml` alineado con la norma y traducido al inglés | 07-api | Carlos | ✅ Mergeado — [#33](https://github.com/code-corhuila/barber-saas-docs/pull/33), [#34](https://github.com/code-corhuila/barber-saas-docs/pull/34) |
| ADR-004 (microservicios) + ADRs exigidos por la norma (SPEC-011) | 05-architecture | Daniel | ✅ Mergeado — [#20](https://github.com/code-corhuila/barber-saas-docs/pull/20), [#32](https://github.com/code-corhuila/barber-saas-docs/pull/32) |
| Gobernanza y convenciones git alineadas con la norma | 00-governance | Daniel | ✅ Mergeado — [#28](https://github.com/code-corhuila/barber-saas-docs/pull/28), [#30](https://github.com/code-corhuila/barber-saas-docs/pull/30), [#40](https://github.com/code-corhuila/barber-saas-docs/pull/40) |
| Arquitectura objetivo, despliegue y hexagonal | 05-architecture | Daniel | ✅ Mergeado — [#36](https://github.com/code-corhuila/barber-saas-docs/pull/36), [#37](https://github.com/code-corhuila/barber-saas-docs/pull/37), [#41](https://github.com/code-corhuila/barber-saas-docs/pull/41) |
| Modelo de datos por dominio (UUID, centavos) y consistencia de contratos | 06-data / 07-api | Daniel | ✅ Mergeado — [#38](https://github.com/code-corhuila/barber-saas-docs/pull/38), [#39](https://github.com/code-corhuila/barber-saas-docs/pull/39) |
| Traducción al inglés de los documentos restantes | todas | Daniel | ✅ Mergeado — [#42](https://github.com/code-corhuila/barber-saas-docs/pull/42) |
| Diagramas C4, UML (secuencia/estado) y ER por dominio | 08-diagrams | Daniel | ✅ Mergeado — [#44](https://github.com/code-corhuila/barber-saas-docs/pull/44)…[#48](https://github.com/code-corhuila/barber-saas-docs/pull/48) |
| Ítems rojos de gobernanza, arquitectura y datos + revisión | 00 / 05 / 06 | Carlos | ✅ Mergeado — [#49](https://github.com/code-corhuila/barber-saas-docs/pull/49), [#50](https://github.com/code-corhuila/barber-saas-docs/pull/50) |
| SPEC-008 (WIP limit, story map, backlog MVP2) — arrastre de la semana 08 | 03 / 04 / 15 | Juan Pablo | 🟡 En revisión — issue [#51](https://github.com/code-corhuila/barber-saas-docs/issues/51), PRs [#52](https://github.com/code-corhuila/barber-saas-docs/pull/52), [#53](https://github.com/code-corhuila/barber-saas-docs/pull/53), [#54](https://github.com/code-corhuila/barber-saas-docs/pull/54) |

## WIP limit aplicado

Límite definido en SPEC-008: In Progress ≤ 1 e In Review ≤ 2 por persona. Mi trabajo
individual lo respetó: tuve un solo ítem en curso (los contratos OpenAPI). Se trabajó en
serie, con commits del 28/09 entre las 16:42 y las 16:52, y quedó un único PR en revisión
(#35). A nivel de equipo el límite **no se respetó**: hubo 7 PRs abiertos en revisión a la vez (#36–#42, entre
las 04:11 y las 05:11 UTC del 29/09) y otros 5 a la vez el 30/09 (#44–#48). Queda registrado como
hallazgo.

## Daily sync (async)

Formato: ayer / hoy / bloqueos.

| Fecha | Ayer | Hoy | Bloqueos |
|-------|------|-----|----------|
| 2026-09-28 | Cierre de la semana 08: commit `32a1408` (WEEKLY) y push de la rama `docs/008-agile-process-executable` (DOCS) | Escribir los 5 contratos OpenAPI que faltaban (`566aed8`, `c7dfc5b`, `d29be4f`, `0915077`, `babb39a`) e indexarlos (`839bb1e`) → PR #35, aprobado por `ariel5253` y mergeado | Los 5 repos `-api` están vacíos, así que los contratos se derivaron de los controladores y DTOs del monolito (`barber-saas@develop`). Las decisiones abiertas quedaron como OQ-06…OQ-11 en `07-api/open-questions.md` |
| 2026-09-29 | PR #35 mergeado | Sin commits míos. El equipo mergeó #36–#42 (arquitectura, datos y traducción) | — |
| 2026-09-30 | — | Sin commits míos. El equipo mergeó #44–#50 (diagramas e ítems rojos) | — |
| 2026-10-01 | — | Escribí estas sesiones y el hu-status de la semana 09. Abrí el issue #51 y partí SPEC-008 en los PRs #52–#54 (`cherry-pick -x` desde `6d7436f`) para cumplir el límite de 400 líneas | #53 sigue en 467 líneas (justificado en el PR); conflicto de IDs con los issues #21–#24 |

## Throughput

- **Equipo:** 21 PRs mergeados en DOCS entre el 28/09 y el 30/09 (Daniel 16, Carlos 4,
  Juan Pablo 1), según `gh pr list --search "merged:2026-09-28..2026-10-04"`.
- **Individual:** 1 PR (#35) con 6 commits y +4087 líneas en 7 archivos:
  5 contratos YAML, `07-api/README.md` y `07-api/open-questions.md`.
  Además, 3 PRs abiertos el 2026-10-01 (#52–#54, SPEC-008), todavía en revisión.

## Referencias

- SPEC-011 → `_ecosistema/specs/SPEC-011-adr-topologia-y-decisiones-norma.md`
- Contratos → `barber-saas-docs/07-api/contracts/openapi/`
