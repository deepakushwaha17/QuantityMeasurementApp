package com.api.config;

import com.api.handler.OAuth2LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public SecurityConfig(OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // ✅ Permit all auth endpoints
                        .pathMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/oauth2/**",                          // ✅ OAuth2 flow
                                "/login/oauth2/code/**",               // ✅ Google callback
                                "/oauth2/authorization/**"             // ✅ Google redirect
                        ).permitAll()
                        .anyExchange().permitAll()
                )
                // ✅ Enable OAuth2 login with success handler
                .oauth2Login(oauth2 -> oauth2
                        .authenticationSuccessHandler(oAuth2LoginSuccessHandler)
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}