package com.devdooly.skeleton.core.thread;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class VirtualThreadTest {

    @Test
    public void threadTest1() {
//        Runnable task = () -> {
//            System.out.println("Hello World!");
//        };

        Task task = new Task();
        Thread.startVirtualThread(task);
    }

    @Test
    public void threadTest2() {
        Task task = new Task();
        Thread vThread = Thread.ofVirtual().start(task);
    }

    @Test
    public void threadTest3() {
        Task task = new Task();
        Thread vThread = Thread.ofVirtual().unstarted(task);
        vThread.start();
    }

    @Test
    public void threadTest4() {
        Task task = new Task();
        Executor vExecutor = Executors.newVirtualThreadPerTaskExecutor();
        vExecutor.execute(task);
    }

    @Test
    public void threadTest5() {
        Task task = new Task();
        ThreadFactory vThreadFactory = Thread.ofVirtual().name("vt-", 1).factory();
        Executor vExecutor = Executors.newThreadPerTaskExecutor(vThreadFactory);
        vExecutor.execute(task);
    }

    class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello World!");
        }
    }
}
