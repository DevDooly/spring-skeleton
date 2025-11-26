package com.devdooly.skeleton.common.concurrent;

public class ThreadPoolUtils {

    public static void runOnVirtual(Runnable runnable) {
        Thread.ofVirtual().start(runnable);
    }
}

