package com.devdooly.skeleton.listener.zombie.web;

import com.devdooly.skeleton.core.service.ShutdownService;
import com.devdooly.skeleton.listener.zombie.core.ZombieListener;

public class ZombieShutdownService implements ShutdownService {
    private final ZombieListener zombieListener;

    public ZombieShutdownService(ZombieListener zombieListener) {
        this.zombieListener = zombieListener;
    }

    @Override
    public boolean isShutdownable() {
        return zombieListener.hasFinished();
    }

    @Override
    public void drain() {
        zombieListener.stop();
    }

}
