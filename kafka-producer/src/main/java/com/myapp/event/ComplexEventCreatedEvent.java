package com.myapp.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import com.myapp.domain.ComplexEvent;

/**
 * this is an event which will be trigger after saving the data
 */
@Getter
public class ComplexEventCreatedEvent extends ApplicationEvent {
    private final ComplexEvent complexEvent;

    public ComplexEventCreatedEvent(Object source, ComplexEvent complexEvent) {
        super(source);
        this.complexEvent = complexEvent;
    }
}

