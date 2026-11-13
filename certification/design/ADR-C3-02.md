# ADR-C3-02: Run the services on CCE across two AZs with a horizontal autoscaler per service

## Status

Proposed

## Context

The platform runs 12 containers with Docker Compose on one `Standard_B2as_v2` VM (ADR-020):
- the gateway;
- eight domain APIs;
- `workflow` and `worker`.

That VM is a single point of failure. Its CPU comes in bursts and can be throttled under sustained load, and the
only way to grow is a bigger VM. Section 2 of the design note (Reliability and Performance efficiency) and
section 3 (Scalability) need two things:
- at least one replica of each hot service in each AZ;
- a way to add replicas when the CPU climbs.

ADR-004 ruled out Kubernetes, but because of the team's capacity during the course, not because of the design.

## Decision

I run every service as a Deployment on a CCE cluster whose nodes are spread over AZ1 and AZ2, in the same VPC as
RDS and DDS. A dedicated ELB is the only public entry, and it forwards to the `api-gateway` Service.
- **Health:** each Deployment keeps its 512 MB memory limit and uses `GET /health` as its liveness and readiness probe.
- **Autoscaling:** appointment-api (2–6 pods at 70 % CPU), api-gateway (2–4 at 60 %) and loyalty-api (2–4 at 70 %) get a HorizontalPodAutoscaler.
- **Pods that do not scale:** the worker stays at one pod, as ADR-016 requires.
- **Images:** they come from SWR, tagged with the release tag.

The containers, ports and environment variables are the ones the `deploy/compose.yml` files already declare.

## Alternatives considered

- Keep Docker Compose on one bigger ECS: it is the smallest change, but it only scales vertically and keeps the single point of failure of ADR-020.
- One ECS Auto Scaling group per service: it scales horizontally without Kubernetes, but a VM per JVM service wastes memory on 12 small services, and every service would need its own image and launch template.
- Serverless containers on CCI: there are no nodes to manage, but the per-pod price for services that run all day is higher, and the worker and gateway gain nothing from scaling to zero.

## Consequences

- Positive: one AZ can fail without taking the platform down. appointment-api and the gateway absorb peaks by adding pods. A release rolls out one pod at a time behind readiness probes, which gives a rollback with `kubectl rollout undo` or the previous image tag.
- Negative: the team has to learn and operate Kubernetes manifests, which ADR-004 wanted to avoid. The cluster control plane and the two-AZ minimum cost more than one VM. Pod-to-pod traffic now crosses AZs, so the TLS that the STRIDE model left as P3 becomes necessary.
