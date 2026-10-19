package co.edu.corhuila.barbersaas.loyalty.observability;

import co.edu.corhuila.barbersaas.loyalty.observability.StickerMetrics.Outcome;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** POST /api/v1/loyalty/stickers cut down to the hop that leaves loyalty-api: the appointment check (CP1, step 4). */
@RestController
class StickerController {

    record GrantStickerRequest(UUID clientId, UUID appointmentId) { }

    private static final Logger log = LoggerFactory.getLogger("loyalty.sticker");
    private final AppointmentApiClient appointments;
    private final StickerMetrics metrics;

    StickerController(AppointmentApiClient appointments, StickerMetrics metrics) {
        this.appointments = appointments;
        this.metrics = metrics;
    }

    @PostMapping("/api/v1/loyalty/stickers")
    ResponseEntity<Map<String, Object>> grant(@RequestBody GrantStickerRequest body,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            HttpServletRequest request) {
        long started = System.nanoTime();
        Outcome outcome = decide(body, authorization);
        metrics.grant(outcome, System.nanoTime() - started);
        request.setAttribute(TraceInterceptor.OUTCOME, outcome.label());
        return switch (outcome) {
            case GRANTED -> ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("clientId", body.clientId()));
            case NOT_FOUND -> apiError(HttpStatus.NOT_FOUND, "NOT_FOUND", "Appointment not found");
            default -> apiError(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "unexpected error");
        };
    }

    private Outcome decide(GrantStickerRequest body, String authorization) {
        try {
            if (body.appointmentId() != null && !appointments.exists(body.appointmentId(), authorization)) {
                return Outcome.NOT_FOUND;
            }
        } catch (AppointmentApiClient.DependencyFailure e) {
            log.atError().addKeyValue("event", e.timedOut() ? "dependency.timeout" : "dependency.failure")
                    .addKeyValue("duration_ms", e.elapsedMs())
                    .addKeyValue("dependency", "appointment-api")
                    .addKeyValue("error_code", "INTERNAL_ERROR")
                    .log(e.getMessage());
            return Outcome.FAILED;
        }
        log.atInfo().addKeyValue("event", "loyalty.sticker.granted")
                .addKeyValue("appointment_id", body.appointmentId())
                .log("Sticker granted");
        return Outcome.GRANTED;
    }

    /** The error and message fields of ErrorResponse in _shared.yaml. */
    private static ResponseEntity<Map<String, Object>> apiError(HttpStatus status, String code, String message) {
        Map<String, Object> body = Map.of("error", code, "message", message);
        return new ResponseEntity<>(body, status);
    }
}
