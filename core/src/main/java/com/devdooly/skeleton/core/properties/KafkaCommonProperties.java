package com.devdooly.skeleton.core.properties;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

public interface KafkaCommonProperties {
    String clientId();
    List<String> bootstrapServers();
    String keySerializer();
    String keyDeSerializer();
    String valueSerializer();
    String valueDeSerializer();
    String schemaRegistryUrl();

    @Setter
    @RequiredArgsConstructor
    class VO implements KafkaCommonProperties {
        private final String clientId;
        private List<String> bootstrapServers;
        private String keySerializer;
        private String keyDeSerializer;
        private String valueSerializer;
        private String valueDeSerializer;
        private String schemaRegistryUrl;

        @Override
        public String clientId() {
            return clientId;
        }

        @Override
        public List<String> bootstrapServers() {
            return bootstrapServers;
        }

        @Override
        public String keySerializer() {
            return keySerializer;
        }

        @Override
        public String keyDeSerializer() {
            return keyDeSerializer;
        }

        @Override
        public String valueSerializer() {
            return valueSerializer;
        }

        @Override
        public String valueDeSerializer() {
            return valueDeSerializer;
        }

        @Override
        public String schemaRegistryUrl() {
            return schemaRegistryUrl;
        }

        @Override
        public String toString(){
            return "";
        }
    }
}