package com.devdooly.skeleton.core.core;

public interface ThreadLoop extends Runnable {

    void startLoop();

    void stopLoop();

    boolean isRunning();
}
