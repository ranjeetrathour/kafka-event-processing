package com.myapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String eventType;
    private String userId;
    private String text;
    private String metadataJson;
    private String sourceSystem;
    private String sourceVersion;
    private String originIp;
}
