package com.example.eventmanagementbackend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Tag(name = "Hello Controller", description = "Basic endpoints for eventmanagementbackend")
public class HelloController {

    // PUBLIC_INTERFACE
    @GetMapping("/")
    @Operation(summary = "Welcome endpoint", description = "Returns a welcome message")
    public String hello() {
        return "Hello, Spring Boot! Welcome to eventmanagementbackend";
    }

    // PUBLIC_INTERFACE
    @GetMapping("/docs")
    @Operation(summary = "API Documentation", description = "Redirects to Swagger UI preserving original scheme/host/port")
    public RedirectView docs(HttpServletRequest request) {
        String xfProto = request.getHeader("X-Forwarded-Proto");
        String xfHost = request.getHeader("X-Forwarded-Host");
        String xfPort = request.getHeader("X-Forwarded-Port");

        UriComponentsBuilder b = UriComponentsBuilder.newInstance()
                .scheme(xfProto != null ? xfProto : request.getScheme())
                .host(xfHost != null ? xfHost : request.getServerName())
                .path("/swagger-ui.html");

        if (xfPort != null && !xfPort.isBlank()) {
            b.port(xfPort);
        } else if (request.getServerPort() != 0) {
            b.port(String.valueOf(request.getServerPort()));
        }

        String target = b.build().toUriString();

        RedirectView rv = new RedirectView(target);
        rv.setHttp10Compatible(false);
        return rv;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns application health status")
    public String health() {
        return "OK";
    }

    // PUBLIC_INTERFACE
    @GetMapping("/api/info")
    @Operation(summary = "Application info", description = "Returns application information")
    public String info() {
        return "Spring Boot Application: eventmanagementbackend";
    }
}