package com.devdooly.skeleton.listener.zombie.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private Cache<String, String> zombieCache;

    @Test
    public void testCache() {
        String key = "testKey";
        String value = "testValue";

        // 1. Put data
        cacheService.putData(key, value);

        // 2. Get data - should be from cache
        String retrievedValue = cacheService.getData(key);
        assertEquals(value, retrievedValue);

        // 3. Verify it's in the Caffeine cache directly
        assertEquals(value, zombieCache.getIfPresent(key));

        // 4. Update data
        String newValue = "newValue";
        cacheService.putData(key, newValue);
        assertEquals(newValue, cacheService.getData(key));
        assertEquals(newValue, zombieCache.getIfPresent(key));

        // 5. Remove data
        cacheService.removeData(key);
        assertEquals("Not Found", cacheService.getData(key));
        assertNull(zombieCache.getIfPresent(key));
    }
}
