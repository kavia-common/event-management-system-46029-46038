package com.example.eventmanagementbackend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Event Management API")
                        .description("REST API for event creation, management, and attendance tracking")
                        .version("0.1.0")
                        .contact(new Contact().name("EventMgmt").email("support@example.com")))
                .externalDocs(new ExternalDocumentation().description("Swagger UI").url("/swagger-ui.html"));
    }
}
