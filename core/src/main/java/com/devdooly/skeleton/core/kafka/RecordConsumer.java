package com.devdooly.skeleton.core.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface RecordConsumer<K, V> {
    default void prepare() {}
    void consume(ConsumerRecord<K, V> record);
    default void flushPending() {}
    default void flushAll() {}
}
