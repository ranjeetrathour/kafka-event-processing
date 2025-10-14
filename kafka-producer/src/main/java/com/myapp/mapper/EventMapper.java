package com.myapp.mapper;

import com.myapp.domain.ComplexEvent;
import com.myapp.domain.EventMetadata;
import com.myapp.domain.EventPayload;
import com.myapp.request.EventRequest;
import org.example.dto.ComplexEventDto;
import org.example.enums.EventStatus;
import org.example.util.JsonUtil;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


@Mapper
@Component
public interface EventMapper {

    default ComplexEvent toComplexEvent(ComplexEventDto dto) {
        if (dto == null) return null;

        return ComplexEvent.builder()
                .id(dto.getId())
                .eventType(dto.getEventType())
                .payload(toPayload(dto.getPayloadJson()))
                .metadata(toMetadata(dto.getMetadataJson()))
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    default ComplexEventDto toDto(ComplexEvent event) {
        if (event == null) return null;

        return ComplexEventDto.builder()
                .id(event.getId())
                .eventType(event.getEventType())
                .payloadJson(JsonUtil.toJson(event.getPayload()))
                .metadataJson(JsonUtil.toJson(event.getMetadata()))
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .build();
    }

    default ComplexEvent toComplexEvent(EventRequest request) {
        if (request == null) return null;

        return ComplexEvent.builder()
                .id(UUID.randomUUID().toString())
                .eventType(request.getEventType())
                .payload(EventPayload.builder()
                        .userId(request.getUserId())
                        .text(request.getText())
                        .build())
                .metadata(toMetadata(request.getMetadataJson()))
                .status(EventStatus.NEW)
                .createdAt(LocalDateTime.now())
                .build();
    }


    default EventPayload toPayload(String payloadJson) {
        if (payloadJson == null) return null;
        return JsonUtil.fromJson(payloadJson, EventPayload.class);
    }

    default EventMetadata toMetadata(String metadataJson) {
        if (metadataJson == null) return null;
        return JsonUtil.fromJson(metadataJson, EventMetadata.class);
    }
}


