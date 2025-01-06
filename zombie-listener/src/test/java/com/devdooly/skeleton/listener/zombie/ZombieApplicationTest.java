package com.devdooly.skeleton.listener.zombie;

import com.devdooly.skeleton.listener.zombie.config.ZombieConfig;
import com.devdooly.skeleton.listener.zombie.service.ZombieService;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ZombieApplicationTest.AppStarter.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ZombieApplicationTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({
            ZombieConfig.class
    })
    public static class AppStarter {
        public static void main(String[] args) {
            SpringApplication.run(AppStarter.class, args);
        }

        @Bean
        public ApplicationListener<ApplicationReadyEvent> applicationListener(ZombieService zombieService) {
            return applicationReadyEvent -> {
                String format = "%Y%m%d%H%i%S";
                String now = zombieService.findNowByDateFormat(format);
                System.out.println(now);
            };
        }
    }
}
