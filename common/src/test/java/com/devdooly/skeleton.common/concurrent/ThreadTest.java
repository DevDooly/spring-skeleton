package com.devdooly.skeleton.common.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    @Test
    public void VirtualVsPlatform() {
        ExecutorService executorService = Executors.newSingleThreadExecutor(NamedThreadFactory.create("single"));
    //  ExecutorService executorService = ThreadPoolUtils.createVirtualExecutor("virtual", 0);

    for (int i = 0; i < 10; i++) {
        executorService.submit(() -> {
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            System.out.printf("%s -> done!", Thread.currentThread().getName());
        });
    }

    /**
     *  -Djdk.virtualThreadScheduler.parallelism=1 -Djdk.virtualThreadScheduler.maxPoolSize=1
     */
    System.out.println(System.getProperty("jdk.virtualThreadScheduler.parallelism"));
    System.out.println(System.getProperty("jdk.virtualThreadScheduler.maxPoolSize"));

    try {
        Thread.sleep(15000L);
    } catch (Exception e) {
        e.printStackTrace(System.err);
    }
    System.out.println("finish");
    }
}
