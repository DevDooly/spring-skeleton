package com.devdooly.skeleton.listener.zombie.core;

import com.devdooly.skeleton.avro.schema.UserAvro;
import com.devdooly.skeleton.common.concurrent.VirtualFuture;
import com.devdooly.skeleton.common.type.CodeEnum;
import com.devdooly.skeleton.core.kafka.RecordConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public class ZombieConsumer implements RecordConsumer<String, UserAvro> {

    private final ZombieProcessor zombieProcessor;

    public ZombieConsumer(ZombieProcessor zombieProcessor) {
        this.zombieProcessor = zombieProcessor;
    }

    @Override
    public void consume(ConsumerRecord<String, UserAvro> record) {
        VirtualFuture<CodeEnum> result = new VirtualFuture<>(zombieProcessor::process);
        log.info("result={}", result.join());
    }
}
