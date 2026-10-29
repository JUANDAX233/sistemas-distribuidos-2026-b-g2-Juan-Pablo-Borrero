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
