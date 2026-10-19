# Week 11 Workshop - Observability, SLOs and Incident Response

<!-- CONFIG-START -->
- FULL_NAME: Juan Pablo Borrero Morales
- GITHUB_USER: JUANDAX233
- TEAM: BarberSaaS
- DOCS_REPO: https://github.com/code-corhuila/barber-saas-docs
- REFLECTION_DOC: https://docs.google.com/document/d/1B3urf8BFh60g9s0vP6rt7NgHMuKrxmlXutj1msSNJV8/edit?usp=sharing
- JOURNEY: A barber grants a loyalty sticker to a client (HU-LOY-001) - loyalty-app -> barber-saas-api-gateway -> loyalty-api -> appointment-api, then StickerGranted via outbox_event -> worker -> notifications-api
<!-- CONFIG-END -->

## Scope

| Item | Value |
|---|---|
| Central service | `barber-saas-loyalty-api` (Java 21, Spring Boot) |
| User story | HU-LOY-001, scenario 1 "Sticker granted" |
| Entry point | `POST /api/v1/loyalty/stickers` (`grantSticker`) through `barber-saas-api-gateway` |
| Synchronous dependency | `barber-saas-appointment-api` - `GET /api/v1/appointments/{id}` |
| Asynchronous hop | `StickerGranted` in `loyalty.outbox_event`, relayed by `barber-saas-worker` to `barber-saas-notifications-api` (ADR-016) |

## Deliverables

| Checkpoint | File | Content |
|---|---|---|
| CP1 | [01-journey.md](01-journey.md) | Hops, trace propagation and span tree |
| CP2 | [02-log-contract.md](02-log-contract.md) | Structured log fields, masking and levels |
| CP3 | [03-instrumentation/](03-instrumentation/) | Trace-context filter, logger, propagation, metrics and test |
| CP4 | [04-metrics.md](04-metrics.md) | Golden signals and metric catalog |
