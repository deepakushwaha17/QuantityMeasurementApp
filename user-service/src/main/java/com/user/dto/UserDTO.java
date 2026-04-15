package com.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        @NotBlank
        private String username;
        @NotBlank @Size(min = 6) private String password;
        @NotBlank @Email
        private String email;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String username;
        private String email;
        private String createdAt;
    }

    // Used by Gateway to validate credentials
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class UserValidationResponse {
        private Long id;
        private String username;
        private String email;
        private String password;   // hashed, for Gateway BCrypt check
        private boolean exists;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public static <T> ApiResponse<T> success(String msg, T data) {
            return ApiResponse.<T>builder().success(true).message(msg).data(data).build();
        }
        public static <T> ApiResponse<T> error(String msg) {
            return ApiResponse.<T>builder().success(false).message(msg).build();
        }
    }
}
