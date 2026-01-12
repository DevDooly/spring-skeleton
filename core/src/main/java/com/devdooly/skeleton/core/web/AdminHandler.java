package com.devdooly.skeleton.core.web;

import com.devdooly.skeleton.core.admin.ServerAdministrator;
import com.devdooly.skeleton.core.dto.Check;
import com.devdooly.skeleton.core.dto.Ping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Slf4j
public class AdminHandler {
    private final ServerAdministrator serverAdministrator;
    private final Supplier<Ping> pingSupplier;

    public AdminHandler(ServerAdministrator serverAdministrator, Supplier<Ping> pingSupplier) {
        this.serverAdministrator = serverAdministrator;
        this.pingSupplier = pingSupplier;
    }

    public Mono<ServerResponse> isAlive() {
        return Mono.defer(() -> {
            if (serverAdministrator.isAlive()) {
                return ServerResponse.ok().build();
            } else {
                return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            }
        });
    }

    public Mono<ServerResponse> isReady() {
        return Mono.defer(() -> {
            if (serverAdministrator.isReady()) {
                return ServerResponse.ok().build();
            } else {
                return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            }
        });
    }

    public Mono<ServerResponse> check() {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.fromSupplier(() -> new Check(serverAdministrator.isShutdownable())), Check.class);
    }

    public Mono<ServerResponse> drain() {
        return Mono.defer(() -> {
            if (serverAdministrator.drain()) {
                return ServerResponse.ok().build();
            } else {
                return ServerResponse.accepted().build();
            }
        });
    }

    public Mono<ServerResponse> down() {
        return Mono.defer(() -> {
            if (serverAdministrator.down()) {
                return ServerResponse.ok().build();
            } else {
                return ServerResponse.accepted().build();
            }
        });
    }

    public Mono<ServerResponse> ping() {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.fromSupplier(pingSupplier), Ping.class);
    }
}
