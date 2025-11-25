package com.devdooly.skeleton.common.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(0);
    private final String namePrefix;
    private final int threadPriority;
    private final boolean isDaemon;

    NamedThreadFactory(ThreadGroup threadGroup, String poolName, int threadPriority, boolean isDaemon) {
        this.group = threadGroup;
        this.namePrefix = poolName + "-";
        this.threadPriority = threadPriority;
        this.isDaemon = isDaemon;
    }

    public static NamedThreadFactory createWithNewThreadGroup(String name) {
        return createWithNewThreadGroup(name, Thread.NORM_PRIORITY);
    }

    public static NamedThreadFactory createWithNewThreadGroup(String name, int priority) {
        ThreadGroup threadGroup = new ThreadGroup(name);
        threadGroup.setMaxPriority(Thread.MAX_PRIORITY);
        return new NamedThreadFactory(threadGroup, name, priority, true);
    }

    public static NamedThreadFactory create(String name) {
        return create(name, Thread.currentThread().getPriority());
    }

    public static NamedThreadFactory create(String name, int priority) {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        return new NamedThreadFactory(threadGroup, name, priority, true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, r, namePrefix + getSeq(), 0);
        thread.setDaemon(isDaemon);
        thread.setPriority(threadPriority);
        return thread;
    }

    private String getSeq() {
        return String.valueOf(0xffff & threadNumber.getAndIncrement());
    }
}
