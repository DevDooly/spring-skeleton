package com.devdooly.skeleton.listener.zombie.core;

import com.devdooly.skeleton.avro.schema.UserAvro;
import com.devdooly.skeleton.core.codes.KafkaTopic;
import com.devdooly.skeleton.core.kafka.KafkaListener;
import com.devdooly.skeleton.core.kafka.RecordConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;

@Slf4j
public class ZombieListener extends KafkaListener<String, UserAvro> {
    public ZombieListener(KafkaConsumer<String, UserAvro> kafkaConsumer,
                          RecordConsumer<String, UserAvro> recordConsumer,
                          long messagePoolTimeout,
                          long commitInterval,
                          int commitBatchSize) {
        super(kafkaConsumer, recordConsumer, Collections.singletonList(KafkaTopic.ZOMBIE), messagePoolTimeout, commitInterval, commitBatchSize);
    }

    @Override
    public String name() {
        return "zombie-listener";
    }

}
