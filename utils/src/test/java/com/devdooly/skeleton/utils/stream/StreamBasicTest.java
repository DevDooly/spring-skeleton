package com.devdooly.skeleton.utils.stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamBasicTest {

    @Test
    public void streamMapTest() {
        List<String> words = Arrays.asList("hello", "world");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(5, 5), wordLengths);
    }

    @Test
    public void streamReduceTest() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream().reduce(0, Integer::sum);
        assertEquals(15, sum);
    }
}
