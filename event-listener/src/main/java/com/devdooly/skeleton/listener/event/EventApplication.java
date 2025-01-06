package com.devdooly.skeleton.listener.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.util.EventListener;

@SpringBootApplication
public class EventApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventApplication.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationListener(EventListener eventListener){
        return event -> {
            Logger log = LoggerFactory.getLogger(EventApplication.class);

            log.info("eventListener run. type={}", eventListener.getClass().getSimpleName());
            log.info("ALL SYSTEM GO.");

            System.out.println("asdf");
        };
    }
}
