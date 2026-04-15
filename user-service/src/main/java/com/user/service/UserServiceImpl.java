package com.user.service;

import com.user.dto.UserDTO;
import com.user.exception.UserAlreadyExistsException;
import com.user.exception.UserNotFoundException;
import com.user.model.User;
import com.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository       userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // ─────────────────────────────────────────────────────────────────────────
    // REGISTER
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public UserDTO.UserResponse registerUser(UserDTO.RegisterRequest request) {
        log.info("Registering user: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(
                    "Username '" + request.getUsername() + "' is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email '" + request.getEmail() + "' is already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        User saved = userRepository.save(user);
        log.info("User saved with id: {}", saved.getId());

        return toUserResponse(saved);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET BY USERNAME  (used by Gateway → login)
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public UserDTO.UserValidationResponse getUserByUsername(String username) {
        log.info("Fetching user by username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with username: " + username));

        return toValidationResponse(user);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET BY EMAIL  (used by Gateway → OAuth2)
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public UserDTO.UserValidationResponse getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with email: " + email));

        return toValidationResponse(user);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // REGISTER OAuth2  (auto-register on first Google login)
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public UserDTO.UserResponse registerOAuth2User(UserDTO.RegisterRequest request) {
        log.info("OAuth2 auto-register for email: {}", request.getEmail());

        // If email already exists just return — idempotent
        return userRepository.findByEmail(request.getEmail())
                .map(this::toUserResponse)
                .orElseGet(() -> {

                    // Make username unique if it collides
                    String username = resolveUniqueUsername(request.getUsername());

                    User user = User.builder()
                            .username(username)
                            .password(passwordEncoder.encode(request.getPassword())) // placeholder
                            .email(request.getEmail())
                            .build();

                    return toUserResponse(userRepository.save(user));
                });
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PRIVATE HELPERS
    // ─────────────────────────────────────────────────────────────────────────

    private String resolveUniqueUsername(String base) {
        if (!userRepository.existsByUsername(base)) {
            return base;
        }
        int suffix = 1;
        while (userRepository.existsByUsername(base + suffix)) {
            suffix++;
        }
        return base + suffix;
    }

    private UserDTO.UserResponse toUserResponse(User user) {
        return UserDTO.UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt() != null
                        ? user.getCreatedAt().toString() : null)
                .build();
    }

    private UserDTO.UserValidationResponse toValidationResponse(User user) {
        return UserDTO.UserValidationResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())   // hashed — Gateway uses BCrypt.matches()
                .exists(true)
                .build();
    }
}