package co.edu.corhuila.barbersaas.loyalty.observability;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Continues the caller's traceparent (or starts a trace), answers it in the response, and keeps
 * trace_id, span_id and correlation_id in the MDC so every log line of the request carries them.
 */
public class TraceContextFilter extends OncePerRequestFilter {

    static final String CORRELATION_HEADER = "X-Correlation-Id";
    private static final Logger log = LoggerFactory.getLogger("access");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        TraceContext trace = TraceContext.continueOrStart(request.getHeader(TraceContext.HEADER));
        String correlationId = request.getHeader(CORRELATION_HEADER);
        if (correlationId == null || correlationId.isBlank() || correlationId.length() > 128) {
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put("trace_id", trace.traceId());
        MDC.put("span_id", trace.spanId());
        MDC.put("correlation_id", correlationId);
        response.setHeader(TraceContext.HEADER, trace.header());
        response.setHeader(CORRELATION_HEADER, correlationId);
        long start = System.nanoTime();
        try {
            chain.doFilter(request, response);
        } finally {
            log.atInfo().addKeyValue("event", "request.completed")
                    .addKeyValue("duration_ms", (System.nanoTime() - start) / 1_000_000)
                    .addKeyValue("http_status", response.getStatus())
                    .log("{} {} answered {}", request.getMethod(), request.getRequestURI(), response.getStatus());
            MDC.clear();
        }
    }
}
