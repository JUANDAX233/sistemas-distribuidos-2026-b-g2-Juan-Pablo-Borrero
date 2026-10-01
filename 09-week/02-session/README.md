# Week 09 — Session 2 (Planning)

> Filled 2026-10-01. Planning is based on what DOCS says is still open after this week's
> merges. Nothing below was invented: each item has a file or a PR behind it.

---

## Qué dejó abierto el sprint

| Pendiente | Origen (evidencia) | Sección |
|-----------|--------------------|---------|
| OQ-06…OQ-11: decisiones de contrato abiertas en los 5 servicios nuevos | `07-api/open-questions.md`, agregadas en PR #35 | 07-api |
| OQ-01: referenciar `429 TooManyRequests` desde `auth-service.yaml` | `07-api/open-questions.md`, marcada "partially closed" | 07-api |
| AT-008: los 29 repos de código dicen "LMS Library" en su README | Sección "Contradictions left open" del PR #49 | 09-microservices |
| `09-microservices/service-catalog.md` todavía describe el monolito (AT-006) | PR #49 | 09-microservices |
| Nombres de planes contradictorios (Basico/Pro/Premium vs Starter/Profesional/Premium) | PR #49 | 06-data / 01-context |
| Artefactos de `deployment.md` que no existen todavía (compose, Dockerfiles, migraciones) | Sección "Pending" de `05-architecture/deployment.md` (PR #49) | 10-devops |
| SPEC-008 sin PR (story map, backlog MVP2, sprint-status) | Rama `docs/008-agile-process-executable`, commit `6d7436f`; los archivos no están en `main` | 03 / 04 / 15 |
| Board de GitHub Projects (SPEC-009) | Ya no está bloqueado: `gh auth status` está activo como `JUANDAX233` desde el 2026-09-28 | 15-project-control |

## Selección para la semana 10 — estimación y dependencias

Estimé yo solo, en story points Fibonacci, porque esta semana **no hubo planning poker con
el equipo**. Hay que validar estos números en la próxima sesión conjunta.

| # | Ítem | Responsable propuesto | Estimación (SP) | Depende de |
|---|------|-----------------------|-----------------|------------|
| 1 | Abrir el PR de SPEC-008, rebasado sobre `main` y traducido al inglés (regla de idioma de #42) | Juan Pablo | 3 | — |
| 2 | Cerrar OQ-06…OQ-11 en los contratos o pasarlas a ADR | Juan Pablo | 5 | Decisión del equipo en cada OQ |
| 3 | Cerrar OQ-01: `429` en `auth-service.yaml` | Juan Pablo | 1 | — |
| 4 | Crear el board de GitHub Projects (SPEC-009) | Juan Pablo | 2 | — |
| 5 | Reescribir `service-catalog.md` con los 8 dominios (AT-006) | Por asignar | 3 | ADR-004 (ya mergeado) |
| 6 | PR `chore/` en los 29 repos para corregir el README "LMS Library" (AT-008) | Por asignar | 5 | — |
| 7 | Definir los nombres de planes vigentes | Equipo (decisión) | 1 | Acuerdo de producto |

## Secuencia

1. Los ítems 1, 3 y 4 no tienen dependencias y son cortos. Van primero para respetar el
   WIP ≤ 1.
2. Después el ítem 2, porque varias OQ necesitan acuerdo del equipo.
3. Los ítems 5 a 7 se reparten en la próxima sesión con el equipo.

## Notas de la sesión

- No hubo planning en vivo con Carlos, Daniel y Carolay. Esta planificación es individual y
  sus estimaciones son provisionales.
- La semana 10 es corte (SPEC-011: "el corte de la semana 10 se evalúa con esto"), así que
  se priorizó lo que el revisor puede marcar en rojo.
