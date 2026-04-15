package com.api.handler;

import com.api.client.UserServiceClient;
import com.api.dto.RegisterRequest;
import com.api.dto.UserValidationResponse;
import com.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;

    // ✅ Change this to your frontend URL if you have one
    // For Postman testing keep it as below — token will be in redirect URL
    private static final String REDIRECT_URL = "http://localhost:8080/auth/oauth2/success";

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
                                              Authentication authentication) {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // ── Extract Google user info ───────────────────────────────────────────
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub");

        log.info("OAuth2 login success — email: {} name: {}", email, name);

        // ── Clean username from name ───────────────────────────────────────────
        String username = generateUsername(name, googleId);

        // ── Try find existing user → if not found auto register ───────────────
        return userServiceClient.getUserByEmail(email)
                .flatMap(existingUser -> {
                    log.info("Existing OAuth2 user found: {}", email);
                    return generateTokenAndRedirect(
                            webFilterExchange,
                            existingUser.getUsername(),
                            existingUser.getEmail(),
                            existingUser.getId());
                })
                .onErrorResume(ex -> {
                    // User not found — auto register
                    log.info("New OAuth2 user — auto registering: {}", email);

                    RegisterRequest registerRequest = RegisterRequest.builder()
                            .username(username)
                            .email(email)
                            .password("OAUTH2_" + googleId)   // placeholder password
                            .build();

                    return userServiceClient.registerOAuth2User(registerRequest)
                            .flatMap(newUser ->
                                    generateTokenAndRedirect(
                                            webFilterExchange,
                                            newUser.getUsername(),
                                            newUser.getEmail(),
                                            newUser.getId()))
                            .onErrorResume(registerEx -> {
                                log.error("OAuth2 registration failed: {}",
                                        registerEx.getMessage());
                                return redirectWithError(
                                        webFilterExchange, "Registration failed");
                            });
                });
    }

    // ── Generate token and redirect with token in URL ─────────────────────────
    private Mono<Void> generateTokenAndRedirect(WebFilterExchange webFilterExchange,
                                                String username,
                                                String email,
                                                Long userId) {
        String token = jwtUtil.generateToken(username, email, userId);
        log.info("OAuth2 JWT generated for: {}", username);

        String redirectUrl = UriComponentsBuilder
                .fromUriString(REDIRECT_URL)
                .queryParam("token", token)
                .queryParam("username", username)
                .build().toUriString();

        return redirect(webFilterExchange, redirectUrl);
    }

    // ── Redirect helper ───────────────────────────────────────────────────────
    private Mono<Void> redirect(WebFilterExchange webFilterExchange, String url) {
        ServerHttpResponse response = webFilterExchange
                .getExchange().getResponse();
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create(url));
        return response.setComplete();
    }

    private Mono<Void> redirectWithError(WebFilterExchange webFilterExchange,
                                         String error) {
        String url = UriComponentsBuilder
                .fromUriString(REDIRECT_URL)
                .queryParam("error", error)
                .build().toUriString();
        return redirect(webFilterExchange, url);
    }

    // ── Clean username from Google name ───────────────────────────────────────
//    private String generateUsername(String name, String googleId) {
//        if (name != null && !name.isBlank()) {
//            return name.toLowerCase()
//                    .replaceAll("\\s+", "_")      // spaces → underscores
//                    .replaceAll("[^a-z0-9_]", ""); // remove special chars
//        }
//        return "user_" + googleId.substring(0, 8);
//    }
    private String generateUsername(String name, String googleId) {

        String base = "user";

        if (name != null && !name.isBlank()) {
            base = name.toLowerCase()
                    .replaceAll("\\s+", "_")
                    .replaceAll("[^a-z0-9_]", "");
        }

        // ✅ Ensure uniqueness
        return base + "_" + googleId.substring(0, 5);
    }
}