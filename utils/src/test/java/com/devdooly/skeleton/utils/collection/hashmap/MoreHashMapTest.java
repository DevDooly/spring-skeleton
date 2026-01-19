package com.devdooly.skeleton.utils.collection.hashmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoreHashMapTest {

    @Test
    public void iterateMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("One", 1);
        map.put("Two", 2);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            assertTrue(entry.getKey().matches("One|Two"));
            assertTrue(entry.getValue() == 1 || entry.getValue() == 2);
        }
    }

    @Test
    public void keySet() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);

        Set<String> keys = map.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("A"));
    }

    @Test
    public void getOrDefault() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        assertEquals(Integer.valueOf(1), map.getOrDefault("A", 0));
        assertEquals(Integer.valueOf(0), map.getOrDefault("B", 0));
    }

    @Test
    public void putIfAbsent() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.putIfAbsent("A", 2);
        map.putIfAbsent("B", 2);

        assertEquals(Integer.valueOf(1), map.get("A"));
        assertEquals(Integer.valueOf(2), map.get("B"));
    }
}
