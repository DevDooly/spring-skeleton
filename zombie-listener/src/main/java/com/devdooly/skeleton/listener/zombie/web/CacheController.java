package com.devdooly.skeleton.listener.zombie.web;

import com.devdooly.skeleton.listener.zombie.dto.CacheDataRequest;
import com.devdooly.skeleton.listener.zombie.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("/{key}")
    public Mono<String> getData(@PathVariable String key) {
        return Mono.fromCallable(() -> cacheService.getData(key));
    }

    @PostMapping
    public Mono<String> putData(@RequestBody CacheDataRequest request) {
        return Mono.fromCallable(() -> cacheService.putData(request.getKey(), request.getValue()));
    }

    @DeleteMapping("/{key}")
    public Mono<Void> removeData(@PathVariable String key) {
        return Mono.fromRunnable(() -> cacheService.removeData(key));
    }
}
