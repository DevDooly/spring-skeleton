package com.devdooly.skeleton.utils.collection.hashmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HashMapBasicTest {

    @Test
    public void putAndGet() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);

        assertEquals(Integer.valueOf(10), map.get("Apple"));
        assertEquals(Integer.valueOf(20), map.get("Banana"));
    }

    @Test
    public void remove() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.remove("Apple");
        assertNull(map.get("Apple"));
    }

    @Test
    public void containsKeyAndValue() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        assertTrue(map.containsKey("Apple"));
        assertFalse(map.containsKey("Banana"));
        assertTrue(map.containsValue(10));
        assertFalse(map.containsValue(20));
    }
}
