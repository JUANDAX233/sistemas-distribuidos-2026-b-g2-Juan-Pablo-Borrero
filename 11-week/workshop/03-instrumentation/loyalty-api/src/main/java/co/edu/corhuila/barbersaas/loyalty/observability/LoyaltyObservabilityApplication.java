package co.edu.corhuila.barbersaas.loyalty.observability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LoyaltyObservabilityApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(LoyaltyObservabilityApplication.class, args);
    }

    /** Every API route; the error page would log the same request a second time. */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor()).addPathPatterns("/api/**");
    }
}
