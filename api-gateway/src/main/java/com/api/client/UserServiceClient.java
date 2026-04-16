package com.api.client;

import com.api.dto.*;
import com.api.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceClient {

    private final WebClient webClient;

    // WebClient.Builder is auto-configured as @LoadBalanced by Spring Cloud
    // as long as spring-cloud-starter-loadbalancer is in pom.xml
    public UserServiceClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://user-service")   // Eureka service name
                .build();
    }

    public Mono<UserValidationResponse> getUserByUsername(String username) {
        log.info("Calling → GET /user/{}", username);
        return webClient.get()
                .uri("/user/{username}", username)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> Mono.error(
                                new UserNotFoundException("User not found: " + username)))
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserValidationResponse>>() {
                })
                .map(ApiResponse::getData);
    }

    public Mono<UserValidationResponse> getUserByEmail(String email) {
        log.info("Calling → GET /user/email/{}", email);
        return webClient.get()
                .uri("/user/email/{email}", email)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> Mono.error(
                                new UserNotFoundException("User not found: " + email)))
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserValidationResponse>>() {
                })
                .map(ApiResponse::getData);
    }

    public Mono<UserResponse> registerUser(RegisterRequest request) {
        log.info("Calling → POST /user/register");
        return webClient.post()
                .uri("/user/register")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new RuntimeException("Registration failed: " + body))))
                .onStatus(HttpStatusCode::is5xxServerError,
                        resp -> resp.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new RuntimeException("User service error: " + body))))
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserResponse>>() {
                })
                .map(ApiResponse::getData);
    }

    public Mono<UserResponse> registerOAuth2User(RegisterRequest request) {
        log.info("Calling → POST /user/register");
        return webClient.post()
                .uri("/user/register")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new RuntimeException("OAuth2 registration failed: " + body))))
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserResponse>>() {
                })
                .map(ApiResponse::getData);
    }
}