package com.devdooly.skeleton.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VirtualThreadTest {

    @Test
    public void virtualThreadTest() throws InterruptedException {
        int numberOfThreads = 10;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        long startTime = System.currentTimeMillis();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, numberOfThreads).forEach(i -> {
                executor.submit(() -> {
                    try {
                        System.out.println("Started virtual thread " + i + ": " + Thread.currentThread());
                        Thread.sleep(100);
                        System.out.println("Finished virtual thread " + i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
            });
        }

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
        assertTrue("Execution time should be reasonable", (endTime - startTime) < 5000);
    }
}
