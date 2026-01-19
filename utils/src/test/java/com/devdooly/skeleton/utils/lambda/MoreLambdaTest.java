package com.devdooly.skeleton.utils.lambda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoreLambdaTest {

    @Test
    public void runnableTest() {
        new Thread(() -> System.out.println("Hello from a thread")).start();
    }

    @Test
    public void consumerTest() {
        Consumer<String> printer = System.out::println;
        printer.accept("Hello, Consumer!");
    }

    @Test
    public void functionTest() {
        Function<String, Integer> lengthFunction = String::length;
        assertEquals(Integer.valueOf(5), lengthFunction.apply("Hello"));
    }

    @Test
    public void supplierTest() {
        Supplier<String> helloSupplier = () -> "Hello, Supplier!";
        assertEquals("Hello, Supplier!", helloSupplier.get());
    }

    @Test
    public void customFunctionalInterfaceTest() {
        Greeting greeting = message -> "Hello, " + message;
        assertEquals("Hello, World!", greeting.sayHello("World!"));
    }

    @FunctionalInterface
    interface Greeting {
        String sayHello(String message);
    }
}
