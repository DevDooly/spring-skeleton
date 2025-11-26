package com.devdooly.skeleton.common.concurrent;

import java.util.concurrent.TimeUnit;

public final class Delay {
    private Delay() {}

    public static void sleep(long duration, TimeUnit unit) {
        try {
            unit.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
            throw new RuntimeException("Thread was interrupted during sleep", e);
        }
    }

    public static void sleepMillis(long ms) {
        sleep(ms, TimeUnit.MILLISECONS);
    }

    public static void sleepSeconds(long seconds) {
        sleep(seconds, TimeUnit.SECONDS);
    }
}

