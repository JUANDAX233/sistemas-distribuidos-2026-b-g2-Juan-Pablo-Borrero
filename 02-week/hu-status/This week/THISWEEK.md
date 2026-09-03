# Architecture Decision Record (ADR): Candidate Architecture and Deployment Strategy for BarberSaaS

## Context and Current State
BarberSaaS is a multi-tenant SaaS platform aimed at digitizing the operations of barbershops in Colombia[cite: 1]. Currently, the system is implemented as a **modular monolith** in Spring Boot 3 (Java 21) connected to a shared PostgreSQL database, where multi-tenant isolation is strictly governed by the `barbershop_id` column validated via a `ThreadLocal`-based `TenantContext`[cite: 1].

Because the development team is a single individual at this initial stage and the current operational volume does not justify the operational overhead of a distributed ecosystem (network latency, service discovery, complex distributed consistency, and tracing), **it is decided to maintain a unified deployment (Modular Monolith) for the MVP and Phase 1**, structuring the code internally under strict domain boundaries (Bounded Contexts) that facilitate its future surgical extraction.

---

## 1. Inventory of Bounded Contexts

The BarberSaaS domain is internally divided into the following 9 bounded contexts:

1. **Auth & Identity:** Management of users, credentials, roles (`SUPER_ADMIN`, `ADMIN_BARBERSHOP`, `BARBER`, `CLIENT`), JWT token issuance, and password recovery via 6-digit codes.
2. **Barbershop Management:** Administration of the barbershop profile, cities, account statuses, and general business configuration.
3. **Employee (Staff):** Management of barber employees for each tenant and assignment of their specific profiles.
4. **Schedule & Availability:** Configuration of weekly operating hours, calendar exceptions, and the slot availability calculation engine.
5. **Appointment:** Lifecycle of reservations (6 states: PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW) and handling of records for walk-in clients (*Walk-ins*).
6. **Loyalty & Rewards:** Sticker-based loyalty programs, accumulation rules, redemptions, and automatic generation of reward coupons.
7. **Finance & Inventory:** Manual control of income/expenses, margin calculation, and product stock management with minimum stock alerts.
8. **Notification:** Management of push notifications via Firebase Cloud Messaging (FCM) and emails (SMTP), including prior persistence in the in-app inbox.
9. **Platform Administration (Super Admin):** Centralized management of subscription plans, control of 60-day free trials (*trials*), and global tenant supervision by the BarberSaaS team.

---

## 2. Architecture Decision: What stays together and what separates?

### A. Contexts That Stay Together (Deployed in the Current Modular Monolith)
* **Auth, Barbershop, Employee, Schedule, Appointment, Loyalty, Finance, Inventory, Notification, and Plan** currently coexist within a single deployable package (`barbersaas-backend`) on a single instance on Railway[cite: 1].
* **Why:** 
  - **Alignment with team capacity:** Being a single-developer team, introducing pure microservices would increase the overhead of deployment, network configuration, and debugging by over 300%.
  - **Direct ACID transactions:** Allows guaranteeing strict business constraints (such as the pessimistic `PESSIMISTIC_WRITE` lock on the appointments table to prevent double bookings) through native relational transactions in PostgreSQL without requiring complex two-phase commit (2PC) protocols or distributed sagas.
  - **High-speed in-process communication:** Inter-module calls (e.g., from `Appointment` to `Loyalty` to verify coupons) are executed via in-memory Java method calls, keeping p95 latency under 300 ms[cite: 1].

### B. Candidates for Future Extraction (Independent Services via Triggers)
No component will be proactively extracted; separation is metrically conditioned on the following production operational triggers documented in the PRD[cite: 1]:

1. **`notification-service` (First extraction candidate — Estimated for Phase 2):**
   - *Reason:* External calls to FCM and mail servers are blocking or prone to third-party network latencies.
   - *Extraction trigger:* When notification response time adds more than 200 ms to the appointment creation p95, or when the platform exceeds 5,000 active concurrent barbershops[cite: 1].
2. **`appointment-service` (Estimated for Phase 3):**
   - *Reason:* The booking engine and availability lock is the transactional core with the highest write load under high concurrency.
   - *Extraction trigger:* When monolithic CPU usage sustainedly exceeds 70% during peak hours exclusively due to calendar traffic, or concurrency failures occur due to connection pool saturation[cite: 1].
3. **`auth-service` (Estimated for Phase 3):**
   - *Extraction trigger:* When a second external client application (e.g., a browser-based Corporate Web App) is deployed that needs a unified identity provider independent of the React Native mobile app[cite: 1].

---

## 3. Consequences of the Decision (Trade-offs)

* **Benefits:**
  - Unmatched development and deployment speed (a single command deploys the entire backend to Railway).
  - Simplicity in managing shared relational database schemas under strict logical isolation (`barbershop_id`).
  - Zero network complexity in the MVP.
* **Costs / Mitigated Risks:**
  - Code coupling: Mitigated by strictly prohibiting circular dependencies between domain packages and exclusively using clean service contracts within the monolith.
  - Future horizontal scalability: Solved by ensuring the monolith is completely stateless through the exclusive use of JWT tokens[cite: 1].