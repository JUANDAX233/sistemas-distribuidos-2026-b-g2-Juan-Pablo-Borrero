package co.edu.corhuila.barbersaas.loyalty.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** POST /api/v1/loyalty/stickers reduced to the hop that leaves the service: the appointment check. */
@RestController
class StickerController {

    record GrantStickerRequest(UUID clientId, UUID appointmentId) { }

    private static final Logger log = LoggerFactory.getLogger("loyalty.sticker");
    private final AppointmentApiClient appointments;
    private final MeterRegistry registry;
    private final Timer latency;

    StickerController(AppointmentApiClient appointments, MeterRegistry registry) {
        this.appointments = appointments;
        this.registry = registry;
        this.latency = Timer.builder("loyalty.sticker.request.duration")
                .description("Time to answer POST /api/v1/loyalty/stickers")
                .serviceLevelObjectives(Duration.ofMillis(50), Duration.ofMillis(100), Duration.ofMillis(250),
                        Duration.ofMillis(500), Duration.ofSeconds(1), Duration.ofMillis(2500), Duration.ofSeconds(5))
                .register(registry);
    }

    @PostMapping("/api/v1/loyalty/stickers")
    ResponseEntity<Map<String, Object>> grant(@RequestBody GrantStickerRequest body,
                                              @RequestHeader(value = "Authorization", required = false) String auth) {
        long start = System.nanoTime();
        String outcome = "failed";
        try {
            if (body.appointmentId() != null && !appointments.exists(body.appointmentId(), auth)) {
                outcome = "not_found";
                return ResponseEntity.status(404).body(Map.of("error", "NOT_FOUND", "message", "Appointment not found"));
            }
            outcome = "granted";
            log.atInfo().addKeyValue("event", "loyalty.sticker.granted")
                    .addKeyValue("appointment_id", body.appointmentId()).log("Sticker granted");
            return ResponseEntity.status(201).body(Map.of("clientId", body.clientId()));
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.atError().addKeyValue("event", "dependency.timeout").addKeyValue("dependency", "appointment-api")
                    .addKeyValue("duration_ms", (System.nanoTime() - start) / 1_000_000)
                    .log("appointment-api failed: {}", e.toString());
            return ResponseEntity.status(500).body(Map.of("error", "INTERNAL_ERROR", "message", "unexpected error"));
        } finally {
            Counter.builder("loyalty.sticker.requests").tag("outcome", outcome).register(registry).increment();
            latency.record(Duration.ofNanos(System.nanoTime() - start));
        }
    }
}
