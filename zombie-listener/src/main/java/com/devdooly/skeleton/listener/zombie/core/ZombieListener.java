package com.devdooly.skeleton.listener.zombie.core;

import com.devdooly.skeleton.core.loop.ThreadLoopImpl;
import com.devdooly.skeleton.listener.zombie.service.ZombieService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZombieListener extends ThreadLoopImpl {

    private final ZombieService zombieService;

    public ZombieListener(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    @Override
    protected void process() {
        try {
            log.info("1");
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
