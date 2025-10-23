package com.myapp.consumer;

import com.myapp.propertis.KafkaProperties;
import com.myapp.service.ComplexEventConsumerService;
import lombok.AllArgsConstructor;
import org.example.dto.ComplexEventDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaEventListener {

    private final ComplexEventConsumerService service;
    private final KafkaProperties kafkaProperties;

    /**
     * listen kafka topic and process event to save event into db
     *
     * @param dto dto json data receive from topic
     */
    @KafkaListener(
            topics = "complex-events",
            groupId = "consumer-group-1"
    )
    public void listen(ComplexEventDto dto) {
        service.processEvent(dto);
    }
}
