package com.devdooly.skeleton.core.service;

public interface ShutdownService {

    boolean isShutdownable();

    void drain();
}
