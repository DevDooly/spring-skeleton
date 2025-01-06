package com.devdooly.skeleton.core.config;

import com.devdooly.skeleton.core.service.JdbcDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = JdbcConfigTest.AppStarter.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class JdbcConfigTest {

    @Autowired
    private JdbcDataService jdbcDataService;

    @Test
    public void test() {
        String format = "%Y%m%d%H%i%S";
        String now = jdbcDataService.findNowByDateFormat(format);
        System.out.println(now);
    }

    @Configuration
    @EnableAutoConfiguration
    @Import({
            JdbcConfig.class
    })
    public static class AppStarter {
        public static void main(String[] args) {
            SpringApplication.run(AppStarter.class, args);
        }
    }
}
