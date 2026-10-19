package co.edu.corhuila.barbersaas.loyalty.observability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
public class LoyaltyObservabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoyaltyObservabilityApplication.class, args);
    }

    @Bean
    FilterRegistrationBean<TraceContextFilter> traceContextFilter() {
        FilterRegistrationBean<TraceContextFilter> bean = new FilterRegistrationBean<>(new TraceContextFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
