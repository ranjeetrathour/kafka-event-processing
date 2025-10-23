package com.myapp.service;

import com.myapp.constant.Constants;
import com.myapp.domain.ComplexEvent;
import com.myapp.event.ComplexEventCreatedEvent;
import com.myapp.mapper.EventMapper;
import com.myapp.repository.ComplexEventRepository;
import com.myapp.request.EventRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.GenericException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class ComplexEventService {

    private final ComplexEventRepository repository;
    private final EventMapper eventMapper;
    private final ApplicationEventPublisher applicationEventPublisher;


    /**
     * Saves a new Event to the database and publishes a Spring event
     * so it can be sent to Kafka asynchronously.
     *
     * @param request the event request to persist
     * @return the saved ComplexEvent
     */

    @Transactional
    public ComplexEvent createEvent(EventRequest request) {
        if (Objects.isNull(request)) {
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), Constants.EVENT_ENTITY_CAN_NOT_BE_NULL);
        }
        ComplexEvent event = eventMapper.toComplexEvent(request);
        ComplexEvent saved = repository.save(event);
        try {
            applicationEventPublisher.publishEvent(new ComplexEventCreatedEvent(this, saved));
        } catch (Exception e) {
            log.warn("Spring event publish failed for event id {}: {}", saved.getId(), e.getMessage());
        }

        return saved;
    }
}
