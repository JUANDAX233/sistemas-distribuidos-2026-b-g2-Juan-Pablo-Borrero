package co.edu.corhuila.barbersaas.loyalty.observability;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** getAppointmentById on appointment-api, sent as a child span of the current request. */
@Component
public class AppointmentApiClient {

    static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);
    private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
    private final String baseUrl;

    AppointmentApiClient(@Value("${loyalty.appointment-api-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /** True on 200, false on 404; any other status is an IOException, like a timeout. */
    public boolean exists(UUID appointmentId, String authorization) throws IOException, InterruptedException {
        HttpRequest.Builder request = HttpRequest.newBuilder(URI.create(baseUrl + "/api/v1/appointments/" + appointmentId))
                .timeout(REQUEST_TIMEOUT)
                .header(TraceContext.HEADER, TraceContext.current().child().header())
                .header(TraceContextFilter.CORRELATION_HEADER, MDC.get("correlation_id"))
                .GET();
        if (authorization != null) {
            request.header("Authorization", authorization);
        }
        int status = http.send(request.build(), HttpResponse.BodyHandlers.discarding()).statusCode();
        if (status != 200 && status != 404) {
            throw new IOException("appointment-api answered " + status);
        }
        return status == 200;
    }
}
