package com.devdooly.skeleton.listener.zombie.config;

import com.devdooly.skeleton.avro.schema.TestAvro;
import com.devdooly.skeleton.core.config.CoreConfig;
import com.devdooly.skeleton.core.config.JdbcConfig;
import com.devdooly.skeleton.core.kafka.KafkaConsumerProperties;
import com.devdooly.skeleton.core.kafka.RecordConsumer;
import com.devdooly.skeleton.core.service.JdbcDataService;
import com.devdooly.skeleton.listener.zombie.core.ZombieConsumer;
import com.devdooly.skeleton.listener.zombie.core.ZombieListener;
import com.devdooly.skeleton.listener.zombie.core.ZombieProperties;
import com.devdooly.skeleton.listener.zombie.service.ZombieService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
    public ZombieListener zombieListener(KafkaConsumer<String, TestAvro> kafkaConsumer,
                                         RecordConsumer<String, TestAvro> zombieConsumer,
                                         ZombieProperties zombieProperties) {
        final long messagePollTimeout = zombieProperties.messagePollTimeout;
        final long commitInterval = zombieProperties.commitInterval;
        final int commitBatchSize = zombieProperties.commitBatchSize;
        return new ZombieListener(kafkaConsumer, zombieConsumer, messagePollTimeout, commitInterval, commitBatchSize);
    }

    @Bean
    public KafkaConsumer<String, TestAvro> kafkaConsumer(KafkaConsumerProperties kafkaConsumerProperties) {
        return new KafkaConsumer<>(kafkaConsumerProperties.asProperties());
    }

    @Bean
    public RecordConsumer<String, TestAvro> zombieConsumer(ZombieProcessor zombieProcessor) {
        return new ZombieConsumer(zombieProcessor);
    }

    @Bean
    public ZombieProcessor zombieProcessor() {
        return new ZombieProcessor();
    }

    @Bean
    public ZombieService zombieService(JdbcDataService jdbcDataService) {
        return new ZombieService(jdbcDataService);
    }

}
