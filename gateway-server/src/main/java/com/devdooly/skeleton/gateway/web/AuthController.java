package com.devdooly.skeleton.gateway.web;

import com.devdooly.skeleton.core.dto.LoginRequest;
import com.devdooly.skeleton.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<String> login(@RequestBody LoginRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }
}
