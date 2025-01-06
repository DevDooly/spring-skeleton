package com.devdooly.skeleton.core.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ThreadLoopImpl implements ThreadLoop {

    private volatile boolean active = false;
    private volatile boolean finish = false;

    @Override
    public void run() {
        active = true;
        log.info("{} loop start.", name());
        preProcess();

        while (isActive()) {
            process();
        }

        postProcess();
        finish = true;
        log.info("{} loop end.", name());
    }

    protected abstract void process();

    protected void preProcess() {
    }

    protected void postProcess() {
    }

    public final void stop() {
        if (active) {
            active = false;
        }
    }

    public final boolean isActive() {
        return active;
    }

    public final boolean hasFinished() {
        return finish;
    }
}
