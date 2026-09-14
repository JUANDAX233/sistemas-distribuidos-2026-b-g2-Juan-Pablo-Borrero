# BarberSaaS — Environment Specifications, Config Matrix, Branch Mapping & MVP 2 Orchestration

---

## 1. Environment Definitions and Configuration Matrix

To ensure security, traceability, and proper environment segregation across Railway infrastructure and local development setups, **BarberSaaS** defines three core operational environments:

*   **Development (Local):** Local testing environment utilizing Docker containers for the relational database and local Spring Boot runtime.
*   **Staging (Railway Preview / Private Beta):** Early validation environment for integration testing, QA cycles by the product team, and concurrency stress testing.
*   **Production (Railway Live):** Live execution environment for independent barbershops in intermediate Colombian cities, enforcing strict multi-tenant data isolation (`barbershop_id`) and automated daily backups.

### Configuration Matrix & Secrets Management
To keep secrets strictly out of version control, all configuration is managed via secure environment variables injected through Railway, paired with local `.env.example` templates in the repository.

| Variable Name | Description | Development (Local) | Staging (Railway) | Production (Railway) |
| :--- | :--- | :--- | :--- | :--- |
| `SPRING_PROFILES_ACTIVE` | Active runtime execution profile | `dev` | `staging` | `prod` |
| `DATABASE_URL` | JDBC connection string for PostgreSQL | `jdbc:postgresql://localhost:5432/barbersaas_dev` | *Railway Internal/External URL* | *Railway Secure Production URL* |
| `JWT_SECRET` | Secret cryptographic key for JWT signing and validation | `local_dev_jwt_secret_key_change_me` | *Railway Secret Variable* | *Railway Secret Variable (Rotated)* |
| `TENANT_CONTEXT_HEADER` | HTTP header name for multi-tenant scope isolation | `X-Barbershop-ID` | `X-Barbershop-ID` | `X-Barbershop-ID` |
| `FCM_SERVER_KEY` | Credential for asynchronous push notification dispatch | `mock_fcm_key` | *Staging FCM Key* | *Production FCM Key* |
| `BACKUP_RETENTION_DAYS` | Automated database backup retention policy | `N/A (Local)` | `7 Days` | `30 Days` |

---

## 2. Branch-to-Environment Mapping

The Continuous Integration and Continuous Deployment (CI/CD) pipeline maps code branches to environments as follows:

*   `feature/hu-*` / `dev/*` ➔ **Development (Local):** Individual developer feature branches for modular backend development (Spring Boot 3 / Java 21) and infrastructure tuning.
*   `qa` / `staging` ➔ **Staging (Railway):** Integration branch automatically deployed to the Private Beta environment to validate ACID transactions, pessimistic locking (`PESSIMISTIC_WRITE`), and cross-module connectivity.
*   `main` ➔ **Production (Railway):** Protected stable production branch. Automated deployments to production occur only after rigorous QA validation and version tagging (`v1.0.0+`).

---

## 3. MVP 2 Orchestration Stories & Acceptance Criteria

Transitioning from MVP 1 to MVP 2 focuses on asynchronous event decoupling, concurrency resilience under high demand, and automated disaster recovery.

### HU-ORC-01: Asynchronous Decoupling of Push Notifications and Emails
*   **Description:** As the BarberSaaS backend system, I need to decouple mass push notifications (FCM) and password recovery emails from the main transaction thread so that third-party network latencies do not block critical booking operations.
*   **Acceptance Criteria:**
    1. Upon a successful appointment booking, the notification event is written locally to the database (in-app inbox) atomically within the same ACID transaction.
    2. A secondary asynchronous worker processes the delivery queue targeting FCM/SMTP using an *at-least-once with retries* delivery semantic.
    3. If the FCM or SMTP server fails to respond, the core appointment transaction does not experience a rollback or request delays exceeding acceptable thresholds.

### HU-ORC-02: Extreme Concurrency Handling for Peak Demand Slots
*   **Description:** As a Infrastructure DevOps Engineer, I need database-level concurrency controls to handle high-traffic booking spikes safely without compromising schedule integrity or allowing overselling.
*   **Acceptance Criteria:**
    1. Stress tests validate that utilizing pessimistic write locks (`PESSIMISTIC_WRITE`) prevents 100% of double-booking scenarios on the last available time slot.
    2. The system cleanly returns a structured HTTP conflict response (`409 Conflict`) when multiple clients attempt to book the exact same slot simultaneously.

### HU-ORC-03: Automated Backup Verification and Recovery in Railway
*   **Description:** As a DevOps Engineer, I need automated daily backups and recovery validation configured for our PostgreSQL database instances to comply with data safety and uptime policies.
*   **Acceptance Criteria:**
    1. Automated backups are active on Railway enforcing the strict 30-day retention policy for production workloads.
    2. A documented restore drill is executed periodically in an isolated test database to verify Recovery Time Objectives (RTO) during potential system incidents.