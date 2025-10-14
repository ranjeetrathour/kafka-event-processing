package com.myapp.producer;

import com.myapp.constant.Constants;
import com.myapp.propertis.KafkaProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ComplexEventDto;
import org.example.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class EventKafkaSender {

    private final KafkaTemplate<String, ComplexEventDto> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public void send(ComplexEventDto dto) {
        kafkaTemplate.send(kafkaProperties.getTopic(), String.valueOf(dto.getId()), dto)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Error occurred during sending event to Kafka: {}", dto, ex);
                        throw new GenericException(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                Constants.FAILED_TO_SEND_KAFKA_EVENT + ex.getMessage()
                        );
                    } else {
                        log.info("Event sent successfully to Kafka topic {} with key {}",
                                kafkaProperties.getTopic(), dto.getId());
                    }
                });
    }
}
