package com.user.controller;

import com.user.dto.UserDTO;
import com.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO.ApiResponse<UserDTO.UserResponse>> register(
            @Valid @RequestBody UserDTO.RegisterRequest req) {
        return ResponseEntity.status(201)
                .body(UserDTO.ApiResponse.success("User registered", userService.registerUser(req)));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO.ApiResponse<UserDTO.UserValidationResponse>> getByUsername(
            @PathVariable String username) {
        return ResponseEntity.ok(
                UserDTO.ApiResponse.success("User found", userService.getUserByUsername(username)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO.ApiResponse<UserDTO.UserValidationResponse>> getByEmail(
            @PathVariable String email) {
        return ResponseEntity.ok(
                UserDTO.ApiResponse.success("User found", userService.getUserByEmail(email)));
    }
}
