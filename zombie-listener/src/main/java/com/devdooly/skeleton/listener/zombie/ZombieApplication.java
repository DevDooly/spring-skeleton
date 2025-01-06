package com.devdooly.skeleton.listener.zombie;

import com.devdooly.skeleton.listener.zombie.core.ZombieListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZombieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZombieApplication.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationListener(ZombieListener zombieListener) {
        return event -> {
            Logger log = LoggerFactory.getLogger(ZombieApplication.class);

            log.info("zombieListener start. type={}", zombieListener.getClass().getSimpleName());
            log.info("ALL SYSTEM READY.");

            zombieListener.go();
            log.info("loop start.");
        };
    }
}
