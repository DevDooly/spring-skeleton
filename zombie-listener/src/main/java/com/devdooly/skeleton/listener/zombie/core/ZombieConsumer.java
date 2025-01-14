package com.devdooly.skeleton.listener.zombie.core;

import com.devdooly.skeleton.avro.schema.TestAvro;
import com.devdooly.skeleton.core.kafka.RecordConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ZombieConsumer implements RecordConsumer<String, TestAvro> {

    @Override
    public void consume(ConsumerRecord<String, TestAvro> record) {

    }
}
