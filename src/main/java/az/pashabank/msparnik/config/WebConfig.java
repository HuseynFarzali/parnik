package az.pashabank.msparnik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                  // Allow all paths
                .allowedOrigins("*")  // Allowed origins (can be "*" for all)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allowed HTTP methods
                .allowedHeaders("*")  // Allowed headers (can be "*" for all)
                .allowCredentials(true)  // Whether to allow credentials (cookies, authorization headers, etc.)
                .maxAge(3600);  // Max age for the preflight response in seconds
    }
}