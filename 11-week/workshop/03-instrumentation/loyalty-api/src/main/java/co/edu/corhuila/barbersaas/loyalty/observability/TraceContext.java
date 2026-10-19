package co.edu.corhuila.barbersaas.loyalty.observability;

import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.MDC;

/** W3C Trace Context (version 00): one trace id for the journey, a new span id per hop. */
public record TraceContext(String traceId, String spanId, String flags) {

    public static final String HEADER = "traceparent";
    private static final Pattern FORMAT = Pattern.compile("^00-([0-9a-f]{32})-([0-9a-f]{16})-([0-9a-f]{2})$");
    private static final String ZERO_TRACE = "0".repeat(32);
    private static final String ZERO_SPAN = "0".repeat(16);
    private static final SecureRandom RANDOM = new SecureRandom();

    /** The incoming header continued with a new span, or a new trace when it is missing or invalid. */
    public static TraceContext continueOrStart(String header) {
        Matcher m = header == null ? null : FORMAT.matcher(header.trim());
        if (m == null || !m.matches() || m.group(1).equals(ZERO_TRACE) || m.group(2).equals(ZERO_SPAN)) {
            return new TraceContext(hex(16), hex(8), "01");
        }
        return new TraceContext(m.group(1), hex(8), m.group(3));
    }

    /** The span of the current request, read from the MDC that TraceContextFilter fills. */
    public static TraceContext current() {
        return new TraceContext(MDC.get("trace_id"), MDC.get("span_id"), "01");
    }

    /** A child span of this one, for an outgoing call. */
    public TraceContext child() {
        return new TraceContext(traceId, hex(8), flags);
    }

    public String header() {
        return "00-" + traceId + "-" + spanId + "-" + flags;
    }

    private static String hex(int bytes) {
        byte[] b = new byte[bytes];
        RANDOM.nextBytes(b);
        return HexFormat.of().formatHex(b);
    }
}
