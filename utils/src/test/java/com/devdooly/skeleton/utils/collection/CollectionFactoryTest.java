package com.devdooly.skeleton.utils.collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionFactoryTest {

    @Test
    public void listOfTest() {
        // Java 9+
        List<String> names = List.of("Apple", "Banana", "Cherry");
        assertEquals(3, names.size());
        assertThrows(UnsupportedOperationException.class, () -> names.add("Durian"));
    }

    @Test
    public void mapOfTest() {
        // Java 9+
        Map<String, Integer> fruits = Map.of("Apple", 1, "Banana", 2, "Cherry", 3);
        assertEquals(3, fruits.size());
        assertEquals(Integer.valueOf(2), fruits.get("Banana"));
        assertThrows(UnsupportedOperationException.class, () -> fruits.put("Durian", 4));
    }
}
