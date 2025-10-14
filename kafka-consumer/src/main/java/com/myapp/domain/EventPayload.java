package com.myapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EventPayload {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Column(name = "sentiment")
    private Double sentimentScore;

    @Column(name = "metadata_json", columnDefinition = "text")
    private String metadataJson;

}

