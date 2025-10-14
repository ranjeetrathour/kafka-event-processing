package com.myapp.controller;

import com.myapp.domain.ComplexEvent;
import com.myapp.request.EventRequest;
import com.myapp.service.ComplexEventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@AllArgsConstructor
public class EventController {

    private final ComplexEventService service;

    @PostMapping
    public ComplexEvent createEvent(@RequestBody EventRequest request) {
        return service.createEvent(request);
    }
}
