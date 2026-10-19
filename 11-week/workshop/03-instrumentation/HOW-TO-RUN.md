# How to Run - loyalty-api Instrumentation

The project in `loyalty-api/` uses the stack of `barber-saas-loyalty-api`: Java 21, Spring Boot
3.5.6, Maven. It keeps only the sticker hop that leaves the service, `POST /api/v1/loyalty/stickers`
calling `GET /api/v1/appointments/{id}`, with no database.

| File | Role |
|---|---|
| `TraceContext.java` | Parses or starts a W3C `traceparent`, creates child spans |
| `TraceContextFilter.java` | Continues the trace, answers `traceparent`, fills the MDC, logs `request.completed` |
| `LogContractFormatter.java` | Prints each log line with the fields of `02-log-contract.md` |
| `AppointmentApiClient.java` | Sends a child `traceparent` and `X-Correlation-Id` to appointment-api, 3 s timeout |
| `StickerController.java` | Records `loyalty_sticker_requests_total{outcome}` and `loyalty_sticker_request_duration_seconds` |
| `TracePropagationTest.java` | Stubs appointment-api and checks the trace id it receives |

## Requirements

- JDK 21 or newer (`java -version`)
- Maven 3.9 (`mvn -v`)

## Install and test

```bash
cd 11-week/workshop/03-instrumentation/loyalty-api
mvn -B test
```

## Run

```bash
APPOINTMENT_API_URL=http://localhost:8081 PORT=8080 mvn spring-boot:run
curl -i -H "traceparent: 00-5f2c9a7e41b0d3c86e1f04a29b7d58c3-9c41e07a2b5d13f6-01" \
     -H "Content-Type: application/json" \
     -d '{"clientId":"9b2e4c1a-0d3f-4a7e-8c5b-1f2a3b4c5d6e","appointmentId":"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f"}' \
     http://localhost:8080/api/v1/loyalty/stickers
curl -s http://localhost:8080/metrics | grep loyalty_sticker
```

With nothing listening on `APPOINTMENT_API_URL` the grant answers `500` and logs
`dependency.timeout`, which is the failure of `02-log-contract.md`.

## Test output

First run: the trace assertions passed, but `/metrics` answered `404`, because `@SpringBootTest`
turns metric export off. `@AutoConfigureObservability` on the test turns it back on.

```text
[ERROR]   TracePropagationTest.theTraceIdSentByTheCallerIsTheOneSentToAppointmentApi:50
Expecting actual:
  "{"timestamp":"2026-10-19T14:25:32.207+00:00","status":404,"error":"Not Found","path":"/metrics"}"
to contain:
  "loyalty_sticker_requests_total{outcome="granted"}"
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
[INFO] BUILD FAILURE
```

Second run, after the fix:

```text
[INFO]  T E S T S
[INFO] Running co.edu.corhuila.barbersaas.loyalty.observability.TracePropagationTest
{"timestamp":"2026-10-19T14:25:48.584Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"dev","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"951aa32b601def2c","correlation_id":"abcf0fa3-6895-4fda-ab70-4f5f5cbaa514","event":"loyalty.sticker.granted","message":"Sticker granted","duration_ms":0,"appointment_id":"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f"}
{"timestamp":"2026-10-19T14:25:48.600Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"dev","trace_id":"5f2c9a7e41b0d3c86e1f04a29b7d58c3","span_id":"951aa32b601def2c","correlation_id":"abcf0fa3-6895-4fda-ab70-4f5f5cbaa514","event":"request.completed","message":"POST /api/v1/loyalty/stickers answered 201","duration_ms":111,"http_status":201}
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.700 s -- in co.edu.corhuila.barbersaas.loyalty.observability.TracePropagationTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  8.123 s
```
