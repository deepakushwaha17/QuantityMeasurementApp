package com.api.controller;

import com.api.client.UserServiceClient;
import com.api.dto.RegisterRequest;
import com.api.dto.UserValidationResponse;
import com.api.exception.UserNotFoundException;
import com.api.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserServiceClient userServiceClient;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    // ══════════════════════════════════════════════════════════════════════════
    // REGISTER — returns success message ONLY, no token
    // ══════════════════════════════════════════════════════════════════════════
    @PostMapping("/register")
    public Mono<ResponseEntity<RegisterResponse>> register(
            @RequestBody RegisterRequest request) {

        log.info("POST /auth/register → username: {}", request.getUsername());

        return userServiceClient.registerUser(request)
                .map(user ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(RegisterResponse.builder()
                                        .success(true)
                                        .message("User registered successfully. Please login to continue.")
                                        .username(user.getUsername())
                                        .email(user.getEmail())
                                        .build())
                )
                .onErrorResume(ex -> {
                    log.error("Register error: {}", ex.getMessage());
                    return Mono.just(
                            ResponseEntity
                                    .status(HttpStatus.BAD_REQUEST)
                                    .body(RegisterResponse.builder()
                                            .success(false)
                                            .message(ex.getMessage())
                                            .build()));
                });
    }

    // ══════════════════════════════════════════════════════════════════════════
    // OAuth2 SUCCESS — returns token as JSON after Google login
    // Browser hits this after OAuth2 redirect
    // ══════════════════════════════════════════════════════════════════════════
    @GetMapping("/oauth2/success")
    public Mono<ResponseEntity<LoginResponse>> oauth2Success(
            @RequestParam String token,
            @RequestParam String username) {

        log.info("OAuth2 success callback — username: {}", username);

        return Mono.just(
                ResponseEntity.ok(
                        LoginResponse.builder()
                                .success(true)
                                .message("Google login successful")
                                .token(token)
                                .username(username)
                                .build()));
    }

    // ══════════════════════════════════════════════════════════════════════════
    // LOGIN — validates credentials and returns JWT token
    // ══════════════════════════════════════════════════════════════════════════
    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(
            @RequestBody LoginRequest request) {

        log.info("POST /auth/login → identifier: {}", request.getUsernameOrEmail());

        return getUserByIdentifier(request.getUsernameOrEmail())
                .flatMap(user -> {

                    // ── Wrong password ────────────────────────────────────────
                    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        log.warn("Invalid password for: {}", request.getUsernameOrEmail());
                        return Mono.just(
                                ResponseEntity
                                        .status(HttpStatus.UNAUTHORIZED)
                                        .body(LoginResponse.builder()
                                                .success(false)
                                                .message("Invalid username or password")
                                                .build()));
                    }

                    // ── Correct — generate token ──────────────────────────────
                    String token = jwtUtil.generateToken(
                            user.getUsername(), user.getEmail(),user.getId());
                    log.info("Login successful for: {}", user.getUsername());

                    return Mono.just(
                            ResponseEntity.ok(
                                    LoginResponse.builder()
                                            .success(true)
                                            .message("Login successful")
                                            .token(token)
                                            .username(user.getUsername())
                                            .email(user.getEmail())
                                            .build()));
                })
                .onErrorResume(UserNotFoundException.class, ex -> {
                    log.warn("User not found: {}", request.getUsernameOrEmail());
                    return Mono.just(
                            ResponseEntity
                                    .status(HttpStatus.UNAUTHORIZED)
                                    .body(LoginResponse.builder()
                                            .success(false)
                                            .message("Invalid username or password")
                                            .build()));
                })
                .onErrorResume(ex -> {
                    log.error("Login error: {}", ex.getMessage());
                    return Mono.just(
                            ResponseEntity
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(LoginResponse.builder()
                                            .success(false)
                                            .message("Login failed: " + ex.getMessage())
                                            .build()));
                });
    }


    // ══════════════════════════════════════════════════════════════════════════
    // PRIVATE
    // ══════════════════════════════════════════════════════════════════════════
    private Mono<UserValidationResponse> getUserByIdentifier(String identifier) {
        if (identifier.contains("@")) {
            return userServiceClient.getUserByEmail(identifier);
        }
        return userServiceClient.getUserByUsername(identifier);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // REQUEST DTO
    // ══════════════════════════════════════════════════════════════════════════
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String usernameOrEmail;
        private String password;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // RESPONSE DTOs — split into two separate classes
    // ══════════════════════════════════════════════════════════════════════════

    // ── Register — NO token field ─────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterResponse {
        private boolean success;
        private String message;
        private String username;
        private String email;
    }

    // ── Login — WITH token field ──────────────────────────────────────────────
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private boolean success;
        private String message;
        private String token;
        private String username;
        private String email;
    }
}