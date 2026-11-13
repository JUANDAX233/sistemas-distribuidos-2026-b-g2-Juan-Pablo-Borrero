# ADR-C3-01: PostgreSQL on RDS as a primary/standby pair with backups in a second region

## Status

Proposed

## Context

Seven of the eight domains keep their schema in one PostgreSQL instance (ADR-011). Today that instance is a
container on the only VM of ADR-020. Its data survives through a `pg_dumpall` that `backup.sh` runs once a day,
and the copy stays on the same disk until someone moves it by hand. That gives an RPO of 24 hours and no RTO
anyone can promise, and the `appointment` schema holds the bookings, the reviews and the outbox the worker relays.
Section 4 of the design note sets an RTO of 30 minutes and an RPO of 5 minutes, and section 2 (Reliability)
names the single VM as the first weakness.

## Decision

I move the PostgreSQL instance to RDS for PostgreSQL 16 in primary/standby mode, with the primary in AZ1 and a
synchronous standby in AZ2, and automatic failover. Automated backups run daily, WAL is archived every 5 minutes,
and both are replicated to an OBS bucket in a second region and kept for 30 days. Nothing else changes:
- the seven schemas;
- the `<domain>_app` users, each allowed to write only to its own schema;
- the Liquibase changelogs in each `-db` repository.

The services only change their `DATABASE_URL` to the RDS private address.

## Alternatives considered

- Keep PostgreSQL as a container on the VM with the daily `pg_dumpall`: it costs nothing more, but it keeps an RPO of 24 hours and a single point of failure, which is what section 4 must fix.
- Run a second ECS with a self-managed streaming replica: it reaches a similar RPO, but the team would have to operate failover, replication lag and WAL archiving by hand, with no DBA on a four-person team.
- Move to GaussDB: it is Huawei's own engine with strong availability, but changing the engine means new drivers, a new review of every Liquibase changeset and a new ADR-006, with no gain the course needs.

## Consequences

- Positive: an AZ failure is handled automatically in about a minute with no committed transaction lost. A region loss is bounded to 5 minutes of data. Backups no longer depend on someone remembering to copy them.
- Negative: RDS primary/standby costs more than one container, roughly double the instance price because the standby is billed too. The migration needs a maintenance window to dump and restore the current data. Liquibase runners must reach RDS from inside the VPC.
