package com.devdooly.skeleton.core.web;

import com.devdooly.skeleton.core.dto.ErrorResponse;
import com.devdooly.skeleton.core.properties.CoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CustomWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final CoreProperties coreProperties;
    private final Set<Class<? extends Throwable>> _4XX;

    public CustomWebExceptionHandler(ErrorAttributes errorAttributes,
                                     WebProperties webProperties,
                                     ApplicationContext applicationContext,
                                     ServerCodecConfigurer serverCOdecConfigurer,
                                     CoreProperties coreProperties) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        this.coreProperties = coreProperties;

        _4XX = new HashSet<>(List.of(IllegalArgumentException.class));
    }

    @Override
    protected RouterFunction<ServerResponse> getRouterFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderResponse);
    }

    private Mono<ServerResponse> renderResponse(ServerRequest request) {
        return Mono
                .defer(() -> {
                    final ErrorResponse errorResponse = buildErrorResponse(request);
                    log.debug("web exception! {}", errorResponse);
                    return Mono.just(errorResponse);
                })
                .flatMap(errorResponse -> ServerResponse
                    .status(errorResponse.status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(errorResponse), ErrorResponse.class));
    }

    private ErrorResponse buildErrorResponse(ServerRequest request) {
        Throwable error = getError(request);
        int status = resolveStatus(error);
        String message = resolveMessage(error);
        return new ErrorResponse(status, request.path(), message, NetUtils.getRemoteAddress(request), coreProperties.serverId);
    }

    private int resolveStatus(Throwable throwable) {
        if (throwable == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        } else if (throwable instanceof ResponseStatusException) {
            return ((ResponseStatusException) throwable).getStatusCode().value();
        } else if (_4XX.contains(throwable.getClass())) {
            return HttpStatus.BAD_REQUEST.value();
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }

    private String resolveMessage(Throwable throwable) {
        return throwable == null ? "unknown" : StringUtils.truncate(throwable.getMessage(), 100);
    }
}
            
