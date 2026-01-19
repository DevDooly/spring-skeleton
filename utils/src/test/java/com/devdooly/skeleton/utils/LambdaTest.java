package com.devdooly.skeleton.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {

    @Test
    public void lambdaTest() {
        List<String> names = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> filteredNames = names.stream()
                .filter(name -> name.startsWith("B"))
                .collect(Collectors.toList());
        assertEquals(1, filteredNames.size());
        assertEquals("Banana", filteredNames.get(0));
    }

    @Test
    public void predicateTest() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Predicate<Integer> isEven = number -> number % 2 == 0;
        List<Integer> evenNumbers = numbers.stream()
                .filter(isEven)
                .collect(Collectors.toList());
        assertEquals(5, evenNumbers.size());
    }
}
