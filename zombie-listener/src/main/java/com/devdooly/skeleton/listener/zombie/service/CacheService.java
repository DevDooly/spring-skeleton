package com.devdooly.skeleton.listener.zombie.service;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final Cache<String, String> zombieCache;

    public String getData(String key) {
        log.info("Fetching data from Caffeine for key: {}", key);
        String value = zombieCache.getIfPresent(key);
        return value != null ? value : "Not Found";
    }

    public String putData(String key, String value) {
        log.info("Putting data to Caffeine for key: {}", key);
        zombieCache.put(key, value);
        return value;
    }

    public void removeData(String key) {
        log.info("Removing data from Caffeine for key: {}", key);
        zombieCache.invalidate(key);
    }
}
