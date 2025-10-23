package com.myapp.controller;

import com.myapp.domain.ComplexEvent;
import com.myapp.service.ComplexEventConsumerService;
import com.myapp.service.KafkaLatestRecordService;
import lombok.AllArgsConstructor;
import org.example.dto.ComplexEventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/consumer")
public class ConsumerEventController {
    private final ComplexEventConsumerService complexEventConsumerService;
    private final KafkaLatestRecordService kafkaLatestRecordService;

    @GetMapping("/all-data")
    public List<ComplexEvent> complexEvent() {
        return complexEventConsumerService.getAllData();
    }


    /**
     * this api will fetch latest data from the kafak either commited or unconnetd
     *
     * @return
     */
    @GetMapping("/kafka/latest")
    public ResponseEntity<ComplexEventDto> getLatestRecord() {
        return kafkaLatestRecordService.fetchLatestRecord()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(new ComplexEventDto()));
    }


}
