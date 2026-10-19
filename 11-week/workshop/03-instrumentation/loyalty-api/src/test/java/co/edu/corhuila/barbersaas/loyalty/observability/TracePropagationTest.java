package co.edu.corhuila.barbersaas.loyalty.observability;

import static org.assertj.core.api.Assertions.assertThat;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@AutoConfigureObservability
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TracePropagationTest {

    static final String TRACE_ID = "5f2c9a7e41b0d3c86e1f04a29b7d58c3";
    static final String SENT = "00-" + TRACE_ID + "-9c41e07a2b5d13f6-01";
    static final AtomicReference<String> received = new AtomicReference<>();
    static final HttpServer appointmentApi = stubAppointmentApi();

    @Autowired
    TestRestTemplate rest;

    @DynamicPropertySource
    static void appointmentApiUrl(DynamicPropertyRegistry registry) {
        registry.add("loyalty.appointment-api-url", () -> "http://localhost:" + appointmentApi.getAddress().getPort());
    }

    @Test
    void theTraceIdSentByTheCallerIsTheOneSentToAppointmentApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("traceparent", SENT);
        String body = "{\"clientId\":\"9b2e4c1a-0d3f-4a7e-8c5b-1f2a3b4c5d6e\","
                + "\"appointmentId\":\"1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f\"}";

        ResponseEntity<String> response = rest.postForEntity("/api/v1/loyalty/stickers", new HttpEntity<>(body, headers), String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(received.get()).matches("00-" + TRACE_ID + "-[0-9a-f]{16}-01").isNotEqualTo(SENT);
        assertThat(response.getHeaders().getFirst("traceparent")).startsWith("00-" + TRACE_ID + "-");
        assertThat(rest.getForObject("/metrics", String.class))
                .contains("loyalty_sticker_requests_total{outcome=\"granted\"}")
                .contains("loyalty_sticker_request_duration_seconds_bucket");
    }

    @AfterAll
    static void stop() {
        appointmentApi.stop(0);
    }

    private static HttpServer stubAppointmentApi() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
            server.createContext("/api/v1/appointments/", exchange -> {
                received.set(exchange.getRequestHeaders().getFirst("traceparent"));
                byte[] json = "{\"clientId\":\"9b2e4c1a-0d3f-4a7e-8c5b-1f2a3b4c5d6e\"}".getBytes();
                exchange.sendResponseHeaders(200, json.length);
                exchange.getResponseBody().write(json);
                exchange.close();
            });
            server.start();
            return server;
        } catch (java.io.IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
