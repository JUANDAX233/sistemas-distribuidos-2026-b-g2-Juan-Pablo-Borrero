package co.edu.corhuila.barbersaas.loyalty.observability;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/** A {@code traceparent} header of W3C Trace Context, version 00: trace id, span id and the sampled flag. */
public final class Traceparent {

    public static final String HEADER = "traceparent";

    private final String traceId;
    private final String spanId;
    private final boolean sampled;

    private Traceparent(String traceId, String spanId, boolean sampled) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.sampled = sampled;
    }

    /** Empty when the header is missing, malformed, of another version, or carries an all-zero id. */
    public static Optional<Traceparent> parse(String header) {
        if (header == null) {
            return Optional.empty();
        }
        String[] part = header.trim().split("-", -1);
        if (part.length != 4 || !part[0].equals("00") || !lowerHex(part[1], 32) || !lowerHex(part[2], 16)
                || !lowerHex(part[3], 2) || allZero(part[1]) || allZero(part[2])) {
            return Optional.empty();
        }
        return Optional.of(new Traceparent(part[1], part[2], (Integer.parseInt(part[3], 16) & 1) == 1));
    }

    /** The first span of a trace that starts here. */
    public static Traceparent startTrace() {
        return new Traceparent(UUID.randomUUID().toString().replace("-", ""), newSpanId(), true);
    }

    /** Same trace and sampling decision under a new span id: the span of the next hop. */
    public Traceparent nextSpan() {
        return new Traceparent(traceId, newSpanId(), sampled);
    }

    public String traceId() {
        return traceId;
    }

    public String spanId() {
        return spanId;
    }

    public String toHeader() {
        return "00-" + traceId + "-" + spanId + (sampled ? "-01" : "-00");
    }

    private static boolean lowerHex(String value, int length) {
        return value.length() == length && value.chars().allMatch(c -> (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f'));
    }

    private static boolean allZero(String value) {
        return value.chars().allMatch(c -> c == '0');
    }

    private static String newSpanId() {
        long id;
        do {
            id = ThreadLocalRandom.current().nextLong();
        } while (id == 0);
        return String.format("%016x", id);
    }
}
