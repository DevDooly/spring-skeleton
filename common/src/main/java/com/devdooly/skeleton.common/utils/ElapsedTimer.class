package com.devdooly.skeleton.common.utils;

import java.util.concurrent.TimeUnit;

public final class ElapsedTimer {
    public static ElapsedTimer startTimer() {
        final ElapsedTimer timer = new ElapsedTimer(false);
        timer.start();
        return timer;
    }

    public static ElapsedTimer startNanoTimer() {
        final ElapsedTimer timer = new ElapsedTimer(true);
        timer.start();
        return timer;
    }

    private final boolean nano;
    private long start;
    private long lap;
    private long end;

    public ElapsedTimer(boolean nano) {
        this.nano = nano;
        reset();
    }

    public void start() {
        start = System.nanoTime();
        lap = start;
    }

    public long lap() {
        if (lap < 0) {
            return 0;
        }

        long cur = System.nanoTime();
        long lapped = cur - lap;
        lap = cur;

        return nano ? lapped : TimeUnit.NANOSECONDS.toMillis(lapped);
    }

    public long stop() {
        if (start < 0) {
            return 0;
        }

        end = System.nanoTime();
        final long elapsed = end - start;
        return nano ? elapsed : TimeUnit.NANOSECONDS.toMillis(elapsed);
    }

    public long elapsed() {
        if (start < 0) {
            return 0;
        }

        final long elapsed = System.nanoTime() - start;
        return nano ? elapsed : TimeUnit.NANOSECONDS.toMillis(elapsed);
    }

    public void reset() {
        start = -1;
        lap = -1;
        end = -1;
    }
}
