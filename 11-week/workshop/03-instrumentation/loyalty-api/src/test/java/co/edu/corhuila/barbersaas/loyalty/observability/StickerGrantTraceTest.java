package co.edu.corhuila.barbersaas.loyalty.observability;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

/** CP3: the barber's trace id reaches appointment-api, and /metrics shows each outcome. */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureObservability
class StickerGrantTraceTest {

    static final String BARBER_TRACE = "3a9e5c7b1d2f4e6a8c0b9d7f5e3a1c2b";
    static final String BARBER_SPAN = "6d2f8a0c4e1b3957";
    static final String FOUND = "1d7c2e9f-6a4b-4c3d-9e8f-7a6b5c4d3e2f";
    static final String SLOW = "5e8a1f3c-2b7d-4c9e-a6f0-8d1b3c5e7a9f";
    static final BlockingQueue<String> seenByAppointmentApi = new LinkedBlockingQueue<>();
    static HttpServer appointmentApi;

    @Autowired
    MockMvc mvc;

    @DynamicPropertySource
    static void appointmentApiStub(DynamicPropertyRegistry properties) throws IOException {
        appointmentApi = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
        appointmentApi.createContext("/api/v1/appointments/", exchange -> {
            seenByAppointmentApi.add(exchange.getRequestHeaders().getFirst(Traceparent.HEADER));
            if (exchange.getRequestURI().getPath().endsWith(SLOW)) {
                sleep(3_500);                                   // longer than the 3 s attempt timeout
            }
            exchange.sendResponseHeaders(200, -1);
            exchange.close();
        });
        appointmentApi.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        appointmentApi.start();
        properties.add("loyalty.appointment-api-url",
                () -> "http://localhost:" + appointmentApi.getAddress().getPort());
    }

    @BeforeEach
    void forgetEarlierCalls() {
        seenByAppointmentApi.clear();
    }

    @Test
    void appointmentApiGetsTheBarbersTraceIdUnderANewSpan() throws Exception {
        String answered = mvc.perform(grant(FOUND).header(Traceparent.HEADER, "00-" + BARBER_TRACE + "-" + BARBER_SPAN + "-01"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader(Traceparent.HEADER);

        Traceparent downstream = Traceparent.parse(seenByAppointmentApi.poll(5, TimeUnit.SECONDS)).orElseThrow();
        assertThat(downstream.traceId()).isEqualTo(BARBER_TRACE);
        assertThat(downstream.spanId()).isNotEqualTo(BARBER_SPAN);
        assertThat(Traceparent.parse(answered).orElseThrow().traceId()).isEqualTo(BARBER_TRACE);
    }

    @Test
    void aSlowAppointmentApiFailsTheGrantAfterOneAttemptAndIsCounted() throws Exception {
        mvc.perform(grant(SLOW)).andExpect(status().isInternalServerError());
        assertThat(seenByAppointmentApi).hasSize(1);            // no retry after a timeout

        String metrics = mvc.perform(get("/metrics")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertThat(metrics)
                .contains("loyalty_sticker_requests_total{outcome=\"failed\"} 1.0")
                .contains("loyalty_sticker_request_duration_seconds_bucket{le=\"5.0\"}")
                .contains("loyalty_dependency_request_duration_seconds_count{dependency=\"appointment-api\",outcome=\"failed\"} 1");
    }

    @AfterAll
    static void stopStub() {
        appointmentApi.stop(0);
    }

    private static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder grant(String appointmentId) {
        return post("/api/v1/loyalty/stickers").contentType(MediaType.APPLICATION_JSON)
                .content("{\"clientId\":\"9b2e4c1a-0d3f-4a7e-8c5b-1f2a3b4c5d6e\",\"appointmentId\":\"" + appointmentId + "\"}");
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
