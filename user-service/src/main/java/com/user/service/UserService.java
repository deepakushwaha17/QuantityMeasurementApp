package com.user.service;

import com.user.dto.UserDTO;

public interface UserService {

    /**
     * Register a brand-new user.
     * Throws UserAlreadyExistsException if username or email is taken.
     */
    UserDTO.UserResponse registerUser(UserDTO.RegisterRequest request);

    /**
     * Fetch user by username.
     * Called by API Gateway to validate login credentials.
     * Throws UserNotFoundException if no match.
     */
    UserDTO.UserValidationResponse getUserByUsername(String username);

    /**
     * Fetch user by email.
     * Called by API Gateway for OAuth2 login flows.
     * Throws UserNotFoundException if no match.
     */
    UserDTO.UserValidationResponse getUserByEmail(String email);

    /**
     * Register an OAuth2 user (no password).
     * Auto-generates a placeholder password internally.
     * Returns existing user silently if email already registered.
     */
    UserDTO.UserResponse registerOAuth2User(UserDTO.RegisterRequest request);
}
