package com.devdooly.skeleton.listener.zombie.config;

import com.devdooly.skeleton.core.web.ShutdownHandler;
import com.devdooly.skeleton.core.web.WebUtils;
import com.devdooly.skeleton.listener.zombie.web.ZombieShutdownService;
import com.devdooly.skeleton.listener.zombie.core.ZombieListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@EnableWebFlux
@Configuration
public class WebConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ShutdownHandler shutdownHandler) {
        final Logger log = LoggerFactory.getLogger(WebConfig.class);

        final Function<ServerRequest, ServerRequest> commonLogging = serverRequest -> {
            log.info("path={}, remote={}", serverRequest.path(), WebUtils.getRemoteHost(serverRequest));
            return serverRequest;
        };

        return route()
                .before(commonLogging)
                .path("/admin", apiBuilder -> apiBuilder
                        .GET("/check", serverRequest -> shutdownHandler.check())
                        .POST("/drain", serverRequest -> shutdownHandler.drain())
                ).build();
    }

    @Bean
    public ShutdownHandler shutdownHandler(ZombieListener zombieListener) {
        return new ShutdownHandler(new ZombieShutdownService(zombieListener));
    }
}

