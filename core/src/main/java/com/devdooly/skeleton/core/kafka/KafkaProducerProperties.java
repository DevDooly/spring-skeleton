package com.devdooly.skeleton.core.kafka;

import com.devdooly.skeleton.core.properties.KafkaCommonProperties;

import java.util.List;
import java.util.Properties;

/**
 * https://docs.confluent.io/current/installation/configuration/producer-configs.html
 */
public interface KafkaProducerProperties extends KafkaCommonProperties {

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

    default String acks() {
        return "1";
    }

    default long bufferMemory() {
        return 33554432L;
    }

    default String compressionType() {
        return null;
    }

    default int retries() {
        return 2147483647;
    }

    default int batchSize() {
        return 16384;
    }

    default long connectionsMaxIdlsMs() {
        return 540000L;
    }

    default long maxBlockMs() {
        return 60000L;
    }

    default int maxRequestSize() {
        return 1048576;
    }

    default int receiveBufferBytes() {
        return 32768;
    }

    default int requestTimeoutMs() {
        return 30000;
    }

    default int sendBufferByres() {
        return 131072;
    }

    default Properties asProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers());
        properties.put("client.id", clientId());
        properties.put("key.serializer", keySerializer());
        properties.put("value.serializer", valueSerializer());
        properties.put("schema.registry.url", schemaRegistryUrl());
        properties.put("acks", acks());
        properties.put("buffer.memory", bufferMemory());
        properties.put("compression.type", compressionType());
        properties.put("retries", retries());
        properties.put("batch,size", batchSize());
        properties.put("connections.max.idle.ms", connectionsMaxIdlsMs());
        properties.put("max.block.ms", maxBlockMs());
        properties.put("max.request.size", maxRequestSize());
        properties.put("receive.buffer.bytes", receiveBufferBytes());
        properties.put("request.timeout.ms", requestTimeoutMs());
        properties.put("send.buffer.bytes", sendBufferByres());
        return properties;
    }
}
