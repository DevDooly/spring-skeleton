package com.devdooly.skeleton.utils.collection.hashset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HashSetBasicTest {

    @Test
    public void addElement() {
        Set<String> set = new HashSet<>();
        set.add("Apple");
        set.add("Banana");
        set.add("Apple"); // Duplicate element

        assertEquals(2, set.size());
        assertTrue(set.contains("Apple"));
    }

    @Test
    public void removeElement() {
        Set<String> set = new HashSet<>();
        set.add("Apple");
        set.add("Banana");

        set.remove("Apple");

        assertEquals(1, set.size());
        assertFalse(set.contains("Apple"));
    }

    @Test
    public void containsElement() {
        Set<String> set = new HashSet<>();
        set.add("Apple");
        assertTrue(set.contains("Apple"));
        assertFalse(set.contains("Banana"));
    }
}
