package co.edu.corhuila.barbersaas.loyalty.observability;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Server span of each request: continues the caller's trace (or starts one), answers it in the
 * response, keeps it as a request attribute for outgoing calls, and puts the ids in the MDC so the
 * log lines of the request carry them.
 */
public class TraceInterceptor implements HandlerInterceptor {

    static final String CORRELATION_HEADER = "X-Correlation-Id";
    /** Request attribute that a controller sets to the outcome label of 04-metrics.md. */
    public static final String OUTCOME = "loyalty.outcome";
    private static final String SPAN = TraceInterceptor.class.getName() + ".span";
    private static final String STARTED = TraceInterceptor.class.getName() + ".started";
    private static final Logger access = LoggerFactory.getLogger("access");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Traceparent span = Traceparent.parse(request.getHeader(Traceparent.HEADER))
                .map(Traceparent::nextSpan)
                .orElseGet(Traceparent::startTrace);
        String correlationId = Optional.ofNullable(request.getHeader(CORRELATION_HEADER))
                .filter(id -> !id.isBlank() && id.length() <= 128)
                .orElseGet(() -> UUID.randomUUID().toString());
        request.setAttribute(SPAN, span);
        request.setAttribute(STARTED, System.nanoTime());
        response.setHeader(Traceparent.HEADER, span.toHeader());
        response.setHeader(CORRELATION_HEADER, correlationId);
        MDC.setContextMap(Map.of("trace_id", span.traceId(), "span_id", span.spanId(), "correlation_id", correlationId));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long elapsedMs = (System.nanoTime() - (long) request.getAttribute(STARTED)) / 1_000_000;
        var line = access.atInfo().addKeyValue("event", "request.completed")
                .addKeyValue("duration_ms", elapsedMs)
                .addKeyValue("http_status", response.getStatus());
        Object outcome = request.getAttribute(OUTCOME);
        if (outcome != null) {
            line = line.addKeyValue("outcome", outcome);
        }
        line.log("{} {} answered {}", request.getMethod(), request.getRequestURI(), response.getStatus());
        MDC.clear();
    }

    /** The server span of the request this thread is serving; empty outside a request. */
    static Optional<Traceparent> currentSpan() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return attributes == null ? Optional.empty()
                : Optional.ofNullable((Traceparent) attributes.getAttribute(SPAN, RequestAttributes.SCOPE_REQUEST));
    }
}
