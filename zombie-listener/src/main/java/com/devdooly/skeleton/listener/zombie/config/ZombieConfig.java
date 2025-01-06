package com.devdooly.skeleton.listener.zombie.config;

import com.devdooly.skeleton.core.config.CoreConfig;
import com.devdooly.skeleton.core.config.JdbcConfig;
import com.devdooly.skeleton.core.service.JdbcDataService;
import com.devdooly.skeleton.listener.zombie.core.ZombieListener;
import com.devdooly.skeleton.listener.zombie.service.ZombieService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        CoreConfig.class,
        JdbcConfig.class
})
public class ZombieConfig {

    @Bean
    public ZombieListener zombieListener(ZombieService zombieService) {
       return new ZombieListener(zombieService);
    }

    @Bean
    public ZombieService zombieService(JdbcDataService jdbcDataService) {
        return new ZombieService(jdbcDataService);
    }

}
