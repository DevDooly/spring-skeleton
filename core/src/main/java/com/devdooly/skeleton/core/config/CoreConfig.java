package com.devdooly.skeleton.core.config;

import com.devdooly.skeleton.core.properties.KafkaCommonProperties;
import com.devdooly.skeleton.core.service.AuthService;
import com.devdooly.skeleton.core.service.JwtUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.management.ManagementFactory;

@Configuration
public class CoreConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }

    @Bean
    public AuthService authService(PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        return new AuthService(passwordEncoder, jwtUtils);
    }

    @Bean
    @ConfigurationProperties(prefix = "skeleton.kafka")
    public KafkaCommonProperties KafkaCommonProperties() {
        return new KafkaCommonProperties.VO(getPid());
    }

    private String getPid() {
        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            return jvmName.split("@")[0];
        } catch (Throwable e) {
            return "?";
        }
    }
}
