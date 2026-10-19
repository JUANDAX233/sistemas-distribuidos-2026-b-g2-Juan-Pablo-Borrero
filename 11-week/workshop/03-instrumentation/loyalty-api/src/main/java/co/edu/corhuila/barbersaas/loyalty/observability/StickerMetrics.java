package co.edu.corhuila.barbersaas.loyalty.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.time.Duration;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

/** The meters of 04-metrics.md for the sticker grant; Actuator serves them on /metrics. */
@Component
public class StickerMetrics {

    /** The outcome label of loyalty_sticker_requests_total; only FAILED burns the error budget (05-slo.md). */
    public enum Outcome {
        GRANTED, RETRIED, REJECTED, NOT_FOUND, FAILED;

        public String label() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    private static final Duration[] BUCKETS = {Duration.ofMillis(50), Duration.ofMillis(100), Duration.ofMillis(250),
            Duration.ofMillis(500), Duration.ofSeconds(1), Duration.ofMillis(2500), Duration.ofSeconds(5)};

    private final MeterRegistry registry;
    private final Map<Outcome, Counter> requests = new EnumMap<>(Outcome.class);
    private final Timer latency;

    StickerMetrics(MeterRegistry registry) {
        this.registry = registry;
        for (Outcome outcome : Outcome.values()) {             // every series exists from the first scrape
            requests.put(outcome, Counter.builder("loyalty.sticker.requests")
                    .description("Sticker grants by outcome").tag("outcome", outcome.label()).register(registry));
        }
        latency = Timer.builder("loyalty.sticker.request.duration")
                .description("Time to answer POST /api/v1/loyalty/stickers")
                .serviceLevelObjectives(BUCKETS).register(registry);
    }

    void grant(Outcome outcome, long elapsedNanos) {
        requests.get(outcome).increment();
        latency.record(elapsedNanos, TimeUnit.NANOSECONDS);
    }

    void dependency(String dependency, String outcome, long elapsedNanos) {
        Timer.builder("loyalty.dependency.request.duration")
                .description("Time of the calls loyalty-api makes to other services")
                .tags("dependency", dependency, "outcome", outcome)
                .serviceLevelObjectives(BUCKETS).register(registry)
                .record(elapsedNanos, TimeUnit.NANOSECONDS);
    }
}
