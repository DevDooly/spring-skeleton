package com.devdooly.skeleton.core.web;

import com.devdooly.skeleton.core.exception.ServerUnavailableException;
import com.devdooly.skeleton.core.admin.ServerAdministrator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class WebHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {
    private final ServerAdministrator serverAdministrator;

    public WebHandlerFilterFunction(ServerAdministrator serverAdministrator) {
        this. serverAdministrator = serverAdministrator;
    }

    @Override
    @NonNull
    public Mono<ServerResponse> filter(@NonNull ServerRequest request, @NonNull HandlerFunction<ServerResponse> next) {
        return Mono.defer(() -> {
            if (!serverAdministrator.isReady()) {
                log.wran("service is unavailable now. status={}", serverAdministrator.getStatus());
                return Mono.error(new ServerUnavailableException("service is unavailable now"));
            } else {
                return next.handle(request);
            }
        });
    }
}

