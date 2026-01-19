package com.devdooly.skeleton.utils.collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoreCollectionTest {

    @Test
    public void setOfTest() {
        // Java 9+
        Set<String> names = Set.of("Apple", "Banana", "Cherry");
        assertEquals(3, names.size());
        assertThrows(UnsupportedOperationException.class, () -> names.add("Durian"));
    }

    @Test
    public void copyOfTest() {
        // Java 10+
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");

        List<String> copy = List.copyOf(list);
        assertThrows(UnsupportedOperationException.class, () -> copy.add("three"));

        list.add("three");
        assertEquals(2, copy.size());
    }
}
