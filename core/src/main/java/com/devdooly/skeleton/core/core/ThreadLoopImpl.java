package com.devdooly.skeleton.core.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ThreadLoopImpl implements ThreadLoop {

    private static final Logger logger = LoggerFactory.getLogger(ThreadLoopImpl.class);

    private volatile boolean running = false;
    private Thread thread;

    @Override
    public void startLoop() {
        if (running) {
            logger.warn("Loop is already running.");
            return;
        }

        running = true;
        thread = new Thread(() -> {
            logger.info("Loop started.");

            while (running) {
                performTask();
            }
            logger.info("Loop stopped.");
        });
        thread.start();
    }

    @Override
    public void stopLoop() {
        if (!running) {
            logger.warn("Loop is not running.");
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while stopping", e);
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    protected void performTask() {

    }
}
