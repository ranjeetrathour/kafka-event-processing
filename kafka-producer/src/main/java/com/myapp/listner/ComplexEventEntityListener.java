package com.myapp.listner;


import com.myapp.domain.ComplexEvent;
import com.myapp.event.ComplexEventCreatedEvent;
import com.myapp.mapper.EventMapper;
import com.myapp.producer.EventKafkaSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crypto.CryptoService;
import org.example.dto.ComplexEventDto;
import org.example.exception.GenericException;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.myapp.constant.Constants.FAILED_TO_SEND_KAFKA_EVENT;


@AllArgsConstructor
@Slf4j
@Component
public class ComplexEventEntityListener {

    private final EventKafkaSender kafkaSender;
    private final EventMapper mapper;
    private final CryptoService cryptoService;

    /**
     * Listens for ComplexEventCreatedEvent and sends the event to Kafka.
     *
     * @param event the event triggered when a ComplexEvent is created
     */
    @EventListener
    public void handleComplexEvent(ComplexEventCreatedEvent event) {
        ComplexEvent complexEvent = event.getComplexEvent();
        if (complexEvent == null) {
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Received null ComplexEvent");
        }

        ComplexEventDto dto = mapper.toDto(complexEvent);
        dto.setMetadataJson(cryptoService.encrypt(dto.getMetadataJson()));
        dto.setPayloadJson(cryptoService.encrypt(dto.getPayloadJson()));
        try {
            kafkaSender.send(dto);
        } catch (Exception e) {
            throw new GenericException(HttpStatus.BAD_GATEWAY.value(), FAILED_TO_SEND_KAFKA_EVENT);
        }
    }


}

