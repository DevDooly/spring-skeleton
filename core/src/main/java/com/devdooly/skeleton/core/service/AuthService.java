package com.devdooly.skeleton.core.service;

import com.devdooly.skeleton.core.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // Lightweight in-memory user database
    private final Map<String, User> userDb = new HashMap<>();

    @PostConstruct
    public void init() {
        // Default users
        userDb.put("admin", User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role("ROLE_ADMIN")
                .build());
        
        userDb.put("user", User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .role("ROLE_USER")
                .build());
        
        log.info("[AuthService] Initialized with default users.");
    }

    public Mono<String> login(String username, String password) {
        return Mono.justOrEmpty(userDb.get(username))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> jwtUtils.generateToken(user.getUsername()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }
    
    public boolean isValidToken(String token) {
        try {
            String username = jwtUtils.extractUsername(token);
            return userDb.containsKey(username) && jwtUtils.validateToken(token, username);
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
}
