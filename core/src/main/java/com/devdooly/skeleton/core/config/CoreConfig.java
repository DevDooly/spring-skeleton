package com.devdooly.skeleton.core.config;

import com.devdooly.skeleton.core.properties.KafkaCommonProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

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
