# How to Run - loyalty-api Instrumentation

`loyalty-api/` uses the stack of `barber-saas-loyalty-api` (Java 21, Spring Boot 3.5.6, Maven)
and keeps only the hop that leaves the service: `POST /api/v1/loyalty/stickers` checking the
visit with `GET /api/v1/appointments/{id}`. No database, so `rejected` and `retried` are never
produced here.

| File | Role |
|---|---|
| `Traceparent.java` | Parses a W3C `traceparent` by hand (version, lengths, lower-case hex, no all-zero ids) and creates new span ids |
| `TraceInterceptor.java` | MVC interceptor on `/api/**`: continues or starts the trace, answers `traceparent` and `X-Correlation-Id`, fills the MDC, logs `request.completed` with `outcome` |
| `LogContractFormatter.java` | Streams each log event with Jackson's `JsonGenerator`, in the field order of `02-log-contract.md` |
| `AppointmentApiClient.java` | JDK `HttpClient` with the limits of the team's `JsonApi`: 2 s connect, 3 s per attempt, one retry after 200 ms on connect errors or 502/503/504, never after a timeout; a new child span per attempt |
| `StickerMetrics.java` | `loyalty_sticker_requests_total{outcome}`, `loyalty_sticker_request_duration_seconds` and `loyalty_dependency_request_duration_seconds{dependency,outcome}`, buckets 0.05-5 s |
| `StickerController.java` | Maps the appointment check to `granted` (201), `not_found` (404) or `failed` (500) |
| `StickerGrantTraceTest.java` | MockMvc against a JDK `HttpServer` stub of appointment-api |

## Requirements

- JDK 21 or newer (`java -version`) and Maven 3.9 (`mvn -v`)

## Install and test

```bash
cd 11-week/workshop/03-instrumentation/loyalty-api
mvn -B test
```

The test checks two things: appointment-api receives the barber's `trace_id` under a new span
id, and a 3.5 s answer fails the grant after one attempt of 3 s and shows up on `/metrics`.

## Run

```bash
APPOINTMENT_API_URL=http://localhost:8081 PORT=8080 mvn spring-boot:run
curl -i -H "traceparent: 00-3a9e5c7b1d2f4e6a8c0b9d7f5e3a1c2b-6d2f8a0c4e1b3957-01" \
     -H "Content-Type: application/json" \
     -d '{"clientId":"9b2e4c1a-0d3f-4a7e-8c5b-1f2a3b4c5d6e","appointmentId":"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f"}' \
     http://localhost:8080/api/v1/loyalty/stickers
curl -s http://localhost:8080/metrics | grep loyalty_
```

With nothing on `APPOINTMENT_API_URL`, the client gets a connection error, retries once after
200 ms and the grant answers `500` with `dependency.failure`.

## What failed while building it

1. First version (delivery 1): `/metrics` answered `404` in the test, because `@SpringBootTest`
   turns metric export off; `@AutoConfigureObservability` turns it back on.
2. This version: both tests passed on the first run, but the `dependency.timeout` line carried
   `"duration_ms":0`. The failure now records the time spent on appointment-api, and the line
   shows the 3 s of the attempt, as in `02-log-contract.md`.

## Test output

Run of 2026-10-19 after the fix; the journey's log lines are kept and Spring's startup lines
are left out.

```text
[INFO]  T E S T S
[INFO] Running co.edu.corhuila.barbersaas.loyalty.observability.StickerGrantTraceTest
{"timestamp":"2026-10-19T20:21:20.328Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"dev","trace_id":"3a9e5c7b1d2f4e6a8c0b9d7f5e3a1c2b","span_id":"31c2f455ad64d142","correlation_id":"fd7be00a-8ff5-4f5f-bd9b-d60f1789d544","event":"loyalty.sticker.granted","message":"Sticker granted","duration_ms":0,"appointment_id":"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f"}
{"timestamp":"2026-10-19T20:21:20.359Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"dev","trace_id":"3a9e5c7b1d2f4e6a8c0b9d7f5e3a1c2b","span_id":"31c2f455ad64d142","correlation_id":"fd7be00a-8ff5-4f5f-bd9b-d60f1789d544","event":"request.completed","message":"POST /api/v1/loyalty/stickers answered 201","duration_ms":232,"http_status":201,"outcome":"granted"}
{"timestamp":"2026-10-19T20:21:23.466Z","level":"ERROR","service":"loyalty-api","version":"2.0.0","environment":"dev","trace_id":"be7d8a9746764115812a2db1d3971e82","span_id":"b07aaac06227a776","correlation_id":"fdf2618b-f78b-4e81-a8d1-c4f61426b3ec","event":"dependency.timeout","message":"appointment-api too slow to answer GET /api/v1/appointments/5e8a1f3c-2b7d-4c9e-a6f0-8d1b3c5e7a9f","duration_ms":3003,"dependency":"appointment-api","error_code":"INTERNAL_ERROR"}
{"timestamp":"2026-10-19T20:21:23.466Z","level":"INFO","service":"loyalty-api","version":"2.0.0","environment":"dev","trace_id":"be7d8a9746764115812a2db1d3971e82","span_id":"b07aaac06227a776","correlation_id":"fdf2618b-f78b-4e81-a8d1-c4f61426b3ec","event":"request.completed","message":"POST /api/v1/loyalty/stickers answered 500","duration_ms":3008,"http_status":500,"outcome":"failed"}
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.799 s -- in co.edu.corhuila.barbersaas.loyalty.observability.StickerGrantTraceTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  12.151 s
```
