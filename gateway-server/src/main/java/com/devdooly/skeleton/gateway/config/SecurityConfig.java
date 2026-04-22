package com.devdooly.skeleton.gateway.config;

import com.devdooly.skeleton.core.config.CoreConfig;
import com.devdooly.skeleton.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@Import(CoreConfig.class)
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthService authService;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**", "/actuator/**", "/scalar/**", "/api-docs/**", "/zombie/scalar/**", "/zombie/api-docs/**").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtFilter(), org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private WebFilter jwtFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (authService.isValidToken(token)) {
                    // In a real app, you would set the SecurityContext here
                    return chain.filter(exchange);
                }
            }
            
            // If it's a public path, let it pass (Spring Security authorizeExchange will handle the final decision)
            String path = exchange.getRequest().getURI().getPath();
            if (path.startsWith("/auth/") || path.contains("/actuator/") || path.contains("/scalar") || path.contains("/api-docs")) {
                return chain.filter(exchange);
            }

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        };
    }
}
