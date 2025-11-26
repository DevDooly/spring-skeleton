package com.devdooly.skeleton.core.admin;

public interface ServerAdministrator {
    long getSince();
    ServerStatus getStatus();
    void startUp();
    boolean isAlive();
    boolean isReady();
    boolean isShutdownable();
    void drain();
    void down();
}
