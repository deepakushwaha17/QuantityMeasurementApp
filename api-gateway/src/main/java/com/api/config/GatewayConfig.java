package com.api.config;

import com.api.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // ── User Service routes (protected) ──
                .route("user-service", r -> r
                        .path("/user/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://user-service"))

                // ── Quantity Service routes (protected) ──
                .route("quantity-service", r -> r
                        .path("/api/v1/quantities/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://quantity-service"))

                .build();
    }
}