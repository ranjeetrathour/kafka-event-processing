package com.myapp.datasimulator;

import com.myapp.domain.EventMetadata;
import com.myapp.request.EventRequest;
import com.myapp.service.ComplexEventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.util.JsonUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class DummyData {

    private final ComplexEventService complexEventService;
    private final Random random = new Random();

    @Scheduled(fixedRate = 3000)
    public void generateDummyEvent() {
        EventMetadata eventMetadata = new EventMetadata("sre", "eree", "12.12.12.13");
        EventRequest request = new EventRequest(
                "DUMMY_EVENT",
                "user_" + random.nextInt(1000),
                "Dummy text " + UUID.randomUUID(),
                JsonUtil.toJson(eventMetadata),
                "SIMULATOR",
                "1.0",
                "127.0.0.1"
        );

        complexEventService.createEvent(request);
        log.info("Dummy event created at {}", LocalDateTime.now());
    }
}
