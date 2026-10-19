package co.edu.corhuila.barbersaas.loyalty.observability;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.logging.structured.StructuredLogFormatter;
import org.springframework.core.env.Environment;

/**
 * Streams each log event as one JSON object in the field order of 02-log-contract.md. Key-value
 * pairs of the event (event, duration_ms and the domain fields) override the defaults.
 */
public class LogContractFormatter implements StructuredLogFormatter<ILoggingEvent> {

    private static final DateTimeFormatter ISO_MILLIS = new DateTimeFormatterBuilder().appendInstant(3).toFormatter();
    private static final List<String> TRACE_KEYS = List.of("trace_id", "span_id", "correlation_id");

    private final JsonFactory factory = new JsonFactory();
    private final String service;
    private final String version;
    private final String environment;

    public LogContractFormatter(Environment env) {
        service = env.getProperty("spring.application.name", "loyalty-api");
        version = env.getProperty("loyalty.version", "unknown");
        environment = env.getProperty("loyalty.environment", "dev");
    }

    @Override
    public String format(ILoggingEvent event) {
        Map<String, Object> pairs = new LinkedHashMap<>();
        if (event.getKeyValuePairs() != null) {
            event.getKeyValuePairs().forEach(kv -> pairs.put(kv.key, kv.value));
        }
        StringWriter out = new StringWriter();
        try (JsonGenerator json = factory.createGenerator(out)) {
            json.writeStartObject();
            json.writeStringField("timestamp", ISO_MILLIS.format(event.getInstant()));
            json.writeStringField("level", event.getLevel().levelStr);
            json.writeStringField("service", service);
            json.writeStringField("version", version);
            json.writeStringField("environment", environment);
            for (String key : TRACE_KEYS) {
                json.writeStringField(key, event.getMDCPropertyMap().get(key));
            }
            write(json, "event", pairs.getOrDefault("event", event.getLoggerName()));
            json.writeStringField("message", event.getFormattedMessage());
            write(json, "duration_ms", pairs.getOrDefault("duration_ms", 0));
            for (Map.Entry<String, Object> pair : pairs.entrySet()) {
                if (!pair.getKey().equals("event") && !pair.getKey().equals("duration_ms")) {
                    write(json, pair.getKey(), pair.getValue());
                }
            }
            if (event.getThrowableProxy() != null) {
                json.writeStringField("error_stack",
                        event.getThrowableProxy().getClassName() + ": " + event.getThrowableProxy().getMessage());
            }
            json.writeEndObject();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return out.append('\n').toString();
    }

    private static void write(JsonGenerator json, String key, Object value) throws IOException {
        json.writeFieldName(key);
        if (value == null) {
            json.writeNull();
        } else if (value instanceof Long || value instanceof Integer) {
            json.writeNumber(((Number) value).longValue());
        } else {
            json.writeString(value.toString());
        }
    }
}
