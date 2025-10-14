package com.myapp.propertis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Holds all Kafka-related configuration values from application.yml
 */
@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private String topic;
    private String keySerializer;
    private String valueSerializer;
    private String groupId;
}
