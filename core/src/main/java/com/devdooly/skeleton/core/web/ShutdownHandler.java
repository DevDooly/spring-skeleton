package com.devdooly.skeleton.core.web;

import com.devdooly.skeleton.core.dto.Check;
import com.devdooly.skeleton.core.service.ShutdownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Slf4j
public class ShutdownHandler {
    private final ShutdownService shutdownService;

    public ShutdownHandler(ShutdownService shutdownService) {
        this.shutdownService = shutdownService;
    }

    // https://docs.spring.io/spring-framework/reference/core/null-safety.html
    @NonNull
    public Mono<ServerResponse> check() {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.fromSupplier(() -> new Check(shutdownService.isShutdownable())), Check.class);
    }

    @NonNull
    public Mono<ServerResponse> drain() {
        return Mono
                .defer(() -> {
                    shutdownService.drain();
                    return Mono.empty();
                }).then(ServerResponse.ok().build());
    }

}
