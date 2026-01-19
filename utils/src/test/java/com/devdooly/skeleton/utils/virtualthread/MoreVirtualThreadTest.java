package com.devdooly.skeleton.utils.virtualthread;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoreVirtualThreadTest {

    @Test
    public void highConcurrencyTest() {
        AtomicInteger counter = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        var threads = IntStream.range(0, 100_000)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
                    try {
                        Thread.sleep(Duration.ofSeconds(1));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    counter.incrementAndGet();
                }))
                .toList();

        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time for 100,000 virtual threads: " + (endTime - startTime) + "ms");

        assertEquals(100_000, counter.get());
    }
}
