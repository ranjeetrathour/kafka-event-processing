package com.myapp.listner;

import com.myapp.domain.ComplexEvent;
import com.myapp.event.ComplexEventCreatedEvent;
import com.myapp.mapper.EventMapper;
import com.myapp.producer.EventKafkaSender;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crypto.CryptoService;
import org.example.dto.ComplexEventDto;
import org.example.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.myapp.constant.Constants.FAILED_TO_SEND_KAFKA_EVENT;
import static com.myapp.constant.Constants.RECEIVED_EVENT_CAN_NOT_BE_NULL;

@AllArgsConstructor
@Slf4j
@Component
public class ComplexEventEntityListener {

    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final EventKafkaSender kafkaSender;
    private final EventMapper mapper;
    private final CryptoService cryptoService;

    /**
     * Listens for ComplexEventCreatedEvent and sends the event to Kafka asynchronously.
     *
     * @param event the event triggered when a ComplexEvent is created
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleComplexEvent(ComplexEventCreatedEvent event) {
        ComplexEvent complexEvent = event.getComplexEvent();
        if (complexEvent == null) {
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), RECEIVED_EVENT_CAN_NOT_BE_NULL);
        }

        ComplexEventDto dto = mapper.toDto(complexEvent);
        dto.setMetadataJson(cryptoService.encrypt(dto.getMetadataJson()));
        dto.setPayloadJson(cryptoService.encrypt(dto.getPayloadJson()));

        try {
            executor.execute(() -> kafkaSender.send(dto));
        } catch (Exception e) {
            throw new GenericException(HttpStatus.BAD_GATEWAY.value(), FAILED_TO_SEND_KAFKA_EVENT);
        }
    }

    /**
     * Gracefully shutdown the executor when Spring context is closing.
     */
    @PreDestroy
    public void shutdownExecutor() {
        log.info("Shutting down executor for Kafka event sending...");
        executor.shutdown();
    }
}
