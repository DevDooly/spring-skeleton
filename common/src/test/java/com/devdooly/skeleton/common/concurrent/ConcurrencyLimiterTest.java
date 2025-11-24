package com.devdooly.skeleton.common.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class ConcurrencyLimiterTest {

    @Test
    public void test() {
        final int concurrency = 3;
        final int taskSize = 5;

        ConcurrencyLimiter concurrencyLimiter = new SemaConcurrencyLimiter(concurrency);
        List<Callable<Integer>> list = new ArrayList<>();

        for (int i = 0; i < taskSize; i++) {
            final int idx = i;
            list.add(() -> {
                Thread thread = Thread.currentThread();
                System.out.printf("start! name=%s, isVirtual=%b, tId=%d, idx=%d%n", thread.getName(), thread.isVirtual(), thread.threadId(), idx);
                sleep(3000L);
                System.out.printf("end! name=%s, isVirtual=%b, tId=%d, idx=%d%n", thread.getName(), thread.isVirtual(), thread.threadId(), idx);
                return idx;
            });
        }

        ThreadFactory vtf = Thread.ofVirtual()
                .name("vTest-", 0)   // prefix=vTest-, 번호 0부터 증가
                .factory();

        List<Future<Integer>> fList;
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(vtf)) {
            fList = list.stream()
                    .map(c -> executor.submit(concurrencyLimiter.wrap(c)))
                    .toList();
        }

        fList.forEach(f -> {
            try {
                Integer i = f.get();
                System.out.println(i);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("done");
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
