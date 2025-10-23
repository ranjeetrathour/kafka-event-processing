package com.myapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "complex_event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplexEvent {

    @Id
    private String id;

    private String eventType;

    @Embedded
    private EventPayload payload;

    @Embedded
    private EventMetadata metadata;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreation() {
        this.id = UUID.randomUUID().toString();
    }


}

