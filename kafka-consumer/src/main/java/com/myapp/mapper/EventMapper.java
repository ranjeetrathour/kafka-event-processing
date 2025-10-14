package com.myapp.mapper;

import com.myapp.domain.ComplexEvent;
import com.myapp.domain.EventMetadata;
import com.myapp.domain.EventPayload;
import org.example.dto.ComplexEventDto;
import org.example.util.JsonUtil;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


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

    default EventPayload toPayload(String payloadJson) {
        if (payloadJson == null || payloadJson.isBlank()) return null;

        try {
            return JsonUtil.fromJson(payloadJson, EventPayload.class);
        } catch (Exception e) {
            // Log and return null if JSON is invalid
            System.err.println("Failed to parse payload JSON: " + payloadJson);
            return null;
        }
    }

    default EventMetadata toMetadata(String metadataJson) {
        if (metadataJson == null || metadataJson.isBlank()) return null;

        try {
            return JsonUtil.fromJson(metadataJson, EventMetadata.class);
        } catch (Exception e) {
            // Log and return null if JSON is invalid
            System.err.println("Failed to parse metadata JSON: " + metadataJson);
            return null;
        }
    }
}



