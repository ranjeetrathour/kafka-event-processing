package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.EventStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexEventDto {
    private String id;
    private String eventType;
    private String payloadJson;
    private String metadataJson;
    private EventStatus status;
    private LocalDateTime createdAt;

}

