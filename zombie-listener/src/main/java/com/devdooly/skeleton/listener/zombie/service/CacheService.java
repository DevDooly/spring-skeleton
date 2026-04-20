package com.devdooly.skeleton.listener.zombie.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CacheService {

    // Simulating a database or slow data source
    private final Map<String, String> db = new HashMap<>();

    @Cacheable(value = "zombieCache", key = "#key")
    public String getData(String key) {
        log.info("Fetching data from DB for key: {}", key);
        return db.getOrDefault(key, "Not Found");
    }

    @CachePut(value = "zombieCache", key = "#key")
    public String putData(String key, String value) {
        log.info("Putting data to DB and cache for key: {}", key);
        db.put(key, value);
        return value;
    }

    @CacheEvict(value = "zombieCache", key = "#key")
    public void removeData(String key) {
        log.info("Removing data from DB and cache for key: {}", key);
        db.remove(key);
    }
}
