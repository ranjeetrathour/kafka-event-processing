package com.myapp.beans;

import com.myapp.mapper.EventMapper;
import com.myapp.propertis.KafkaProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.example.crypto.CryptoService;
import org.example.crypto.impl.CryptoServiceImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Beans {


    private final KafkaProperties kafkaProperties;

    public Beans(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    @Bean
    public EventMapper eventMapper() {
        return Mappers.getMapper(EventMapper.class);
    }

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(kafkaProperties.getTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public CryptoService cryptoService() {
        return new CryptoServiceImpl();
    }
}
