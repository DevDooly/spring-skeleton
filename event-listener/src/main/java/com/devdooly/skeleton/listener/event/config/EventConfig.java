package com.devdooly.skeleton.listener.event.config;

import com.devdooly.skeleton.core.config.CoreConfig;
import com.devdooly.skeleton.listener.event.core.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(CoreConfig.class)
@Configuration
public class EventConfig {

    @Bean
    public EventListener eventListener() {
        return new EventListener();
    }


}
