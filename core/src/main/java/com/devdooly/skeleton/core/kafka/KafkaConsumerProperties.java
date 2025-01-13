package com.devdooly.skeleton.core.kafka;

import com.devdooly.skeleton.core.properties.KafkaCommonProperties;
import org.apache.kafka.clients.consumer.RangeAssignor;

import java.util.List;
import java.util.Properties;

/**
 * https://docs.confluent.io/current/installation/configuration/consumer-configs.html
 */
public interface KafkaConsumerProperties extends KafkaCommonProperties {

    KafkaCommonProperties kafkaCommonProperties();

    default String clientId() {
        return kafkaCommonProperties().clientId();
    }

    default List<String> bootstrapServers() {
        return kafkaCommonProperties().bootstrapServers();
    }

    default String keySerializer() {
        return kafkaCommonProperties().keySerializer();
    }

    default String keyDeSerializer() {
        return kafkaCommonProperties().keyDeSerializer();
    }

    default String valueSerializer() {
        return kafkaCommonProperties().valueSerializer();
    }

    default String valueDeSerializer() {
        return kafkaCommonProperties().valueDeSerializer();
    }

    default String schemaRegistryUrl() {
        return kafkaCommonProperties().schemaRegistryUrl();
    }

    String groupId();

    default int fetchMinBytes() {
        return 1;
    }

    default int heartbeatIntervalMs() {
        return 3000;
    }

    default int maxPartitionFetchBytes() {
        return 1048576;
    }

    default int sessionTimeoutMs() {
        return 10000;
    }

    default long connectionMaxIdleMs() {
        return 540000L;
    }

    default boolean enableAutoCommit() {
        return true;
    }

    default int maxPollIntervalMs() {
        return 300000;
    }

    default int maxPollRecords() {
        return 500;
    }

    default String partitionAssignmentStrategy() {
        return RangeAssignor.class.getName();
    }

    default int receiveBufferBytes() {
        return 65536;
    }

    default int sendBufferBytes() {
        return 131072;
    }

    default Properties asProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers());
        properties.put("client.id", clientId());
        properties.put("key.deserializer", keyDeSerializer());
        properties.put("value.deserializer", valueDeSerializer());
        properties.put("schema.registry.url", schemaRegistryUrl());
        properties.put("group.id", groupId());
        properties.put("fetch.min.bytes", fetchMinBytes());
        properties.put("heartbeat.interval.ms", heartbeatIntervalMs());
        properties.put("max.partition.fetch.bytes", maxPartitionFetchBytes());
        properties.put("session.timeout.ms", sessionTimeoutMs());
        properties.put("auto.offset.reset", enableAutoCommit());
        properties.put("connections.max.idle.ms", connectionMaxIdleMs());
        properties.put("enable.auto.commit", enableAutoCommit());
        properties.put("max.poll.interval.ms", maxPollIntervalMs());
        properties.put("mas.poll.records", maxPollRecords());
        properties.put("partition.assignment.strategy", partitionAssignmentStrategy());
        properties.put("receive.buffer.bytes", receiveBufferBytes());
        properties.put("send.buffer.bytes", sendBufferBytes());
        properties.put("specific.avro.reader", true);
        return properties;
    }
}