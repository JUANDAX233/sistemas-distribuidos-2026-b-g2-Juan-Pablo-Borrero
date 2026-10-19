package co.edu.corhuila.barbersaas.loyalty.observability;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.logging.structured.StructuredLogFormatter;
import org.springframework.core.env.Environment;

/** One JSON line per event, with the field names of 02-log-contract.md. */
public class LogContractFormatter implements StructuredLogFormatter<ILoggingEvent> {

    private static final DateTimeFormatter UTC_MILLIS =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);
    private final ObjectMapper json = new ObjectMapper();
    private final String service;
    private final String version;
    private final String environment;

    public LogContractFormatter(Environment env) {
        this.service = env.getProperty("spring.application.name", "loyalty-api");
        this.version = env.getProperty("loyalty.version", "unknown");
        this.environment = env.getProperty("loyalty.environment", "dev");
    }

    @Override
    public String format(ILoggingEvent event) {
        Map<String, Object> line = new LinkedHashMap<>();
        line.put("timestamp", UTC_MILLIS.format(event.getInstant()));
        line.put("level", event.getLevel().toString());
        line.put("service", service);
        line.put("version", version);
        line.put("environment", environment);
        Map<String, String> mdc = event.getMDCPropertyMap();
        line.put("trace_id", mdc.get("trace_id"));
        line.put("span_id", mdc.get("span_id"));
        line.put("correlation_id", mdc.get("correlation_id"));
        line.put("event", event.getLoggerName());
        line.put("message", event.getFormattedMessage());
        line.put("duration_ms", 0);
        if (event.getKeyValuePairs() != null) {
            event.getKeyValuePairs().forEach(kv -> line.put(kv.key, kv.value));
        }
        if (event.getThrowableProxy() != null) {
            line.put("error_stack", event.getThrowableProxy().getClassName() + ": "
                    + event.getThrowableProxy().getMessage());
        }
        try {
            return json.writeValueAsString(line) + "\n";
        } catch (JsonProcessingException e) {
            return "{\"level\":\"ERROR\",\"event\":\"log.unwritable\"}\n";
        }
    }
}
