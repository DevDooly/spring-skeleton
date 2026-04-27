package com.devdooly.skeleton.gateway.web;

import com.devdooly.skeleton.core.dto.LoginRequest;
import com.devdooly.skeleton.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<String> login(@RequestBody LoginRequest request) {
        log.info("[AuthController] Login request received for user: {}", request.getUsername());
        return authService.login(request.getUsername(), request.getPassword())
                .doOnSuccess(token -> log.info("[AuthController] Login successful for user: {}", request.getUsername()))
                .doOnError(e -> log.error("[AuthController] Login failed for user: {}. Error: {}", request.getUsername(), e.getMessage()));
    }
}
