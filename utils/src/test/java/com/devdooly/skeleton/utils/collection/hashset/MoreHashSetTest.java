package com.devdooly.skeleton.utils.collection.hashset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoreHashSetTest {

    @Test
    public void iterateSet() {
        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("C");

        // Iteration order is not guaranteed in HashSet
        for (String element : set) {
            assertTrue(element.matches("[A-C]"));
        }
    }

    @Test
    public void createFromList() {
        Set<String> set = new HashSet<>(java.util.Arrays.asList("A", "B", "A"));
        assertEquals(2, set.size());
    }

    @Test
    public void clearSet() {
        Set<String> set = new HashSet<>();
        set.add("one");
        set.clear();
        assertTrue(set.isEmpty());
    }
}
