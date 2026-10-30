# CP4 - Security gates and negative authorization test

## What it covers

| Item | Value |
|---|---|
| Story | [code-corhuila/barber-saas-docs#159](https://github.com/code-corhuila/barber-saas-docs/issues/159) - each worker internal operation requires its own scope |
| Criterion | 1: `POST /internal/v1/subscriptions/expire` on `platform-admin-api` with a worker token without the scope `subscriptions:expire` returns `403` and no barbershop changes status |
| Threat | STRIDE E, risk 6, PR [#154](https://github.com/code-corhuila/barber-saas-docs/pull/154) |
| Test | `WorkerScopeHttpTest` (package `co.edu.corhuila.barbersaas.platformadmin.app`) |
| Workflow | `security.yml`: gitleaks, OWASP dependency-check, the test above |

## Run the test locally

Requirements: Java 21 and Maven, a clone of `code-corhuila/barber-saas-platform-admin-api`
on `develop`. The test reuses `HttpTest` (RS256 test keys, `serviceBearer`) and
`FakeBarbershopApi` of that repository.

```bash
cd barber-saas-platform-admin-api
cp <fork>/13-week/workshop/04-security-ci/WorkerScopeHttpTest.java \
   platform-admin-app/src/test/java/co/edu/corhuila/barbersaas/platformadmin/app/
mvn -B -pl platform-admin-app -am test -Dtest=WorkerScopeHttpTest -Dsurefire.failIfNoSpecifiedTests=false
```

## Expected result

Today the test **fails**: `SubscriptionJobs.expire` only checks that the `sub` is
`barber-saas-worker`, so the call answers `200`. That failure is the gap of story #159. When the
use case also checks the scope `subscriptions:expire`, the test passes and the gate goes green.

## Run the workflow

Copy `security.yml` to `.github/workflows/` of `barber-saas-platform-admin-api` and open a pull
request to `develop`. The workflow lives here as a proposal; it does not change the team
repository today.

## Real run (CP8)

Run on 2026-10-30 on my machine, against `barber-saas-platform-admin-api` on `develop` at
`ebd594b` (Java 25.0.1, Maven 3.9.15). The test file was copied only for the run and removed
afterwards, so the team repository did not change.

```text
$ mvn -B -pl platform-admin-app -am test -Dtest=WorkerScopeHttpTest -Dsurefire.failIfNoSpecifiedTests=false
           Status = 200
             Body = {"suspended":0,"remaining":false}
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 6.334 s <<< FAILURE! -- in co.edu.corhuila.barbersaas.platformadmin.app.WorkerScopeHttpTest
[ERROR] co.edu.corhuila.barbersaas.platformadmin.app.WorkerScopeHttpTest.a_worker_token_without_the_expire_scope_cannot_suspend_barbershops -- Time elapsed: 1.785 s <<< FAILURE!
java.lang.AssertionError: Status expected:<403> but was:<200>
	at co.edu.corhuila.barbersaas.platformadmin.app.WorkerScopeHttpTest.a_worker_token_without_the_expire_scope_cannot_suspend_barbershops(WorkerScopeHttpTest.java:23)
[INFO] platform-admin-app ................................. FAILURE [  9.374 s]
[INFO] BUILD FAILURE
```

This is the expected red result: a worker token with only `sub: barber-saas-worker` and no scope
reaches `POST /internal/v1/subscriptions/expire` and gets `200`, which proves the gap of story
#159, criterion 1. The gate stays red until the use case checks `subscriptions:expire`.

## Workflow validation (CP8)

`actionlint` could not run on my machine (Docker Desktop was stopped), so I validated the file
with `js-yaml` 4.1.0:

```text
js-yaml 4.1.0: security.yml parsed without errors
on: pull_request, workflow_dispatch | pull_request branches: develop, qa, main
job secret-scan: runs-on ubuntu-latest, timeout 5 min, 2 steps
job dependency-audit: runs-on ubuntu-latest, timeout 20 min, 3 steps
job authorization-tests: runs-on ubuntu-latest, timeout 10 min, 3 steps
```

The YAML is valid and the three gates (secret scan, dependency audit, authorization test) are
present. A full `actionlint` check is still pending on the team pipeline.
