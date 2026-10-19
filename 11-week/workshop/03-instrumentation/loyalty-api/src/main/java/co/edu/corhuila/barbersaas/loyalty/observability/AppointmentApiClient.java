package co.edu.corhuila.barbersaas.loyalty.observability;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * getAppointmentById of appointment-service.yaml with the limits of the team's JsonApi: 2 s to
 * connect, 3 s per attempt, and one retry after 200 ms only when appointment-api could not be
 * reached or answered 502/503/504 - never after a timeout. Each attempt is its own child span.
 */
@Component
public class AppointmentApiClient {

    static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(2);
    static final Duration ATTEMPT_TIMEOUT = Duration.ofSeconds(3);
    static final Duration RETRY_DELAY = Duration.ofMillis(200);
    static final int MAX_ATTEMPTS = 2;
    private static final Set<Integer> RETRYABLE = Set.of(502, 503, 504);
    private static final String NAME = "appointment-api";

    private final HttpClient http;
    private final URI baseUri;
    private final StickerMetrics metrics;

    AppointmentApiClient(@Value("${loyalty.appointment-api-url}") String baseUrl, StickerMetrics metrics) {
        this.http = HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT).build();
        this.baseUri = URI.create(baseUrl);
        this.metrics = metrics;
    }

    /** No usable answer from appointment-api; {@code timedOut} separates the two failures in the logs. */
    public static final class DependencyFailure extends RuntimeException {
        private final boolean timedOut;
        private long elapsedMs;

        DependencyFailure(String message, boolean timedOut) {
            super(message);
            this.timedOut = timedOut;
        }

        public boolean timedOut() {
            return timedOut;
        }

        /** Time spent on appointment-api over every attempt, as duration_ms of the log line. */
        public long elapsedMs() {
            return elapsedMs;
        }
    }

    /** True on 200, false on 404 (absent, or another barbershop's); anything else is a DependencyFailure. */
    public boolean exists(UUID appointmentId, String authorization) {
        long started = System.nanoTime();
        String outcome = "failed";
        try {
            boolean found = send(appointmentId, authorization) == 200;
            outcome = found ? "found" : "not_found";
            return found;
        } catch (DependencyFailure e) {
            e.elapsedMs = (System.nanoTime() - started) / 1_000_000;
            throw e;
        } finally {
            metrics.dependency(NAME, outcome, System.nanoTime() - started);
        }
    }

    private int send(UUID appointmentId, String authorization) {
        for (int attempt = 1; ; attempt++) {
            boolean last = attempt == MAX_ATTEMPTS;
            try {
                int status = http.send(request(appointmentId, authorization), HttpResponse.BodyHandlers.discarding())
                        .statusCode();
                if (status == 200 || status == 404) {
                    return status;
                }
                if (last || !RETRYABLE.contains(status)) {
                    throw new DependencyFailure(NAME + " answered " + status, false);
                }
            } catch (HttpTimeoutException e) {
                throw new DependencyFailure(NAME + " too slow to answer GET /api/v1/appointments/" + appointmentId, true);
            } catch (ConnectException e) {
                if (last) {
                    throw new DependencyFailure(NAME + " could not be reached", false);
                }
            } catch (IOException e) {
                throw new DependencyFailure(NAME + " failed: " + e.getMessage(), false);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new DependencyFailure("interrupted while calling " + NAME, false);
            }
            pause();
        }
    }

    private HttpRequest request(UUID appointmentId, String authorization) {
        Traceparent child = TraceInterceptor.currentSpan().orElseGet(Traceparent::startTrace).nextSpan();
        HttpRequest.Builder request = HttpRequest.newBuilder(baseUri.resolve("/api/v1/appointments/" + appointmentId))
                .timeout(ATTEMPT_TIMEOUT)
                .header(Traceparent.HEADER, child.toHeader())
                .GET();
        String correlationId = MDC.get("correlation_id");
        if (correlationId != null) {
            request.header(TraceInterceptor.CORRELATION_HEADER, correlationId);
        }
        if (authorization != null) {
            request.header("Authorization", authorization);
        }
        return request.build();
    }

    private static void pause() {
        try {
            Thread.sleep(RETRY_DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DependencyFailure("interrupted while waiting to retry " + NAME, false);
        }
    }
}
