package com.myapp.event;

import com.myapp.domain.ComplexEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

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

