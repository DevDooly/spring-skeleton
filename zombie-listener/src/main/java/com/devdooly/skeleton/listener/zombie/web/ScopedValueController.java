package com.devdooly.skeleton.listener.zombie.web;

import com.devdooly.skeleton.listener.zombie.service.ScopedValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.StructuredTaskScope;

@Slf4j
@RestController
@RequestMapping("/api/scoped-value")
@RequiredArgsConstructor
public class ScopedValueController {

    private final ScopedValueService scopedValueService;

    @GetMapping("/test")
    public Mono<String> testScopedValue() {
        String requestId = "REQ-" + UUID.randomUUID().toString().substring(0, 8);
        log.info("[Controller] Starting request with id: {}", requestId);

        // Binding ScopedValue
        String result = ScopedValue.where(ScopedValueService.CONTEXT_ID, requestId)
                .get(() -> {
                    // Inside this lambda, ScopedValue is bound
                    String serviceResult = scopedValueService.processInScope();
                    scopedValueService.deepProcess();
                    return serviceResult;
                });

        return Mono.just(result);
    }

    @GetMapping("/structured-task")
    public Mono<String> testStructuredTaskScope() {
        String requestId = "STRUCT-" + UUID.randomUUID().toString().substring(0, 8);
        log.info("[Controller] Starting structured task with id: {}", requestId);

        return Mono.fromCallable(() -> {
            // ScopedValue is inherited by subtasks in StructuredTaskScope
            return ScopedValue.where(ScopedValueService.CONTEXT_ID, requestId).get(() -> {
                try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                    // Forking tasks (using Virtual Threads)
                    var subTask1 = scope.fork(() -> {
                        log.info("[SubTask1] Running in virtual thread");
                        return scopedValueService.processInScope();
                    });

                    var subTask2 = scope.fork(() -> {
                        log.info("[SubTask2] Running in virtual thread");
                        scopedValueService.deepProcess();
                        return "Done";
                    });

                    scope.join();
                    scope.throwIfFailed();

                    return "Main Result: " + subTask1.get() + ", SubResult: " + subTask2.get();
                } catch (Exception e) {
                    log.error("Error in structured task", e);
                    return "Error: " + e.getMessage();
                }
            });
        });
    }
}
