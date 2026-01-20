package com.devdooly.skeleton.utils.stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoreStreamTest {

    @Test
    public void flatMapTest() {
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("a", "b", "c"),
                Arrays.asList("d", "e", "f")
        );

        List<String> flatList = listOfLists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        assertEquals(6, flatList.size());
        assertEquals(Arrays.asList("a", "b", "c", "d", "e", "f"), flatList);
    }

    @Test
    public void findFirstTest() {
        List<String> list = Arrays.asList("Apple", "Banana", "Cherry");
        Optional<String> first = list.stream().findFirst();
        assertTrue(first.isPresent());
        assertEquals("Apple", first.get());
    }

    @Test
    public void anyMatchTest() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        boolean hasEvenNumber = numbers.stream().anyMatch(n -> n % 2 == 0);
        assertTrue(hasEvenNumber);
    }

    @Test
    public void streamOfTest() {
        long count = Stream.of("one", "two", "three").count();
        assertEquals(3, count);
    }

    @Test
    public void filterWithPredicateTest() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

        // Predicate for names starting with 'A'
        java.util.function.Predicate<String> startsWithA = name -> name.startsWith("A");
        List<String> filteredNamesA = names.stream()
                .filter(startsWithA)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList("Alice"), filteredNamesA);

        // Predicate for names longer than 3 characters
        java.util.function.Predicate<String> longerThan3 = name -> name.length() > 3;
        List<String> filteredNamesLongerThan3 = names.stream()
                .filter(longerThan3)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList("Alice", "Charlie", "David"), filteredNamesLongerThan3);

        // Chaining predicates: names starting with 'A' AND longer than 3
        List<String> filteredNamesAAndLongerThan3 = names.stream()
                .filter(startsWithA.and(longerThan3))
                .collect(Collectors.toList());
        assertEquals(Arrays.asList("Alice"), filteredNamesAAndLongerThan3);

        // Chaining predicates: names starting with 'A' OR 'B'
        java.util.function.Predicate<String> startsWithB = name -> name.startsWith("B");
        List<String> filteredNamesAOrB = names.stream()
                .filter(startsWithA.or(startsWithB))
                .collect(Collectors.toList());
        assertEquals(Arrays.asList("Alice", "Bob"), filteredNamesAOrB);

        // Negating a predicate: names NOT starting with 'A'
        List<String> filteredNamesNotA = names.stream()
                .filter(startsWithA.negate())
                .collect(Collectors.toList());
        assertEquals(Arrays.asList("Bob", "Charlie", "David", "Eve"), filteredNamesNotA);
    }
}
