# Week 08 — Session 2 (Planning)

> Filled 2026-09-27 as part of SPEC-010. References DOCS artifacts created the same day by
> SPEC-008 — those are not yet pushed, so no live GitHub URL exists for them yet; the path
> below is the authoritative reference until they're merged.

---

## Story map y backlog MVP2 (referencia, no duplicado)

- Story map del producto → repo DOCS (`code-corhuila/barber-saas-docs`), archivo
  `03-product/story-map.md`, rama `docs/008-agile-process-executable` (sin push aún).
- Backlog de MVP 2 → mismo repo, archivo `03-product/mvp2-backlog.md`, misma rama.

Ambos se construyeron reorganizando contenido ya documentado en `03-product/vision.md` y
`03-product/product-backlog.md` — ningún ítem de alcance nuevo fue inventado (ver la nota de
alcance dentro de cada archivo).

## HUs de MVP2 — estimación y dependencias

Tabla copiada de `03-product/mvp2-backlog.md` (DOCS). Ninguna de estas 6 epics tiene todavía
una sesión de planning poker real — la columna "Estimación" lo refleja explícitamente en vez
de inventar un número:

| ID | Epic | Horizonte | Estimación | Depende de |
|----|------|-----------|------------|------------|
| EP2-001 | Automated Payments | H3 — Growth (Q1–Q2 2027) | Pendiente de sesión de planning poker real | — |
| EP2-002 | Client Web App | H3 — Growth (Q1–Q2 2027) | Pendiente de sesión de planning poker real | — |
| EP2-003 | Advanced Analytics | H3 — Growth (Q1–Q2 2027) | Pendiente de sesión de planning poker real | — |
| EP2-004 | Multi-Location Chains | H4 — Scale (Q3–Q4 2027, sin fecha comprometida) | Pendiente de sesión de planning poker real | — |
| EP2-005 | Marketplace / Discovery | H4 — Scale (sin fecha planeada) | Pendiente de sesión de planning poker real | — |
| EP2-006 | Calendar Integration | H4 — Scale (sin fecha planeada) | Pendiente de sesión de planning poker real | — |

**Por qué "Depende de" está vacío:** `03-product/vision.md` agrupa estos ítems en
horizontes (H3/H4) pero no documenta que uno bloquee a otro — inventar una dependencia acá
sería tan deshonesto como inventar un número de story points.

## Secuenciación propuesta (no vinculante, solo orden de horizonte)

1. **H3 — Growth (antes):** EP2-001, EP2-002, EP2-003 — mismo horizonte en `vision.md`, sin
   orden interno documentado entre ellas.
2. **H4 — Scale (después):** EP2-004, EP2-005, EP2-006 — mismo horizonte, sin orden interno
   documentado entre ellas.

Esta secuenciación es solo el orden de horizonte ya definido en `vision.md` — no es una
decisión de negocio nueva tomada en esta sesión.

## Notas de la sesión

- No hubo sesión de planning poker en vivo con el equipo (Carlos, Juan Pablo, Carolay) esta
  semana — ver Hallazgos en `01-session/README.md`.
- El HU sprint-ready `HU-SADMIN-001-B` (expiración automática de trial) **no es MVP2** — es
  trabajo de MVP actual aún no implementado (F-14, "in progress"). No confundir con esta
  tabla.
