package com.devdooly.skeleton.listener.zombie.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testCache() {
        String key = "testKey";
        String value = "testValue";

        // 1. Put data
        cacheService.putData(key, value);

        // 2. Get data - should be from cache now
        String retrievedValue = cacheService.getData(key);
        assertEquals(value, retrievedValue);

        // 3. Verify it's in the cache
        assertNotNull(cacheManager.getCache("zombieCache"));
        assertEquals(value, cacheManager.getCache("zombieCache").get(key).get());

        // 4. Update data
        String newValue = "newValue";
        cacheService.putData(key, newValue);
        assertEquals(newValue, cacheService.getData(key));
        assertEquals(newValue, cacheManager.getCache("zombieCache").get(key).get());

        // 5. Remove data
        cacheService.removeData(key);
        assertEquals("Not Found", cacheService.getData(key));
        assertEquals(null, cacheManager.getCache("zombieCache").get(key));
    }
}
