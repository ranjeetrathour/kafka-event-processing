package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatus {
    NEW("NEW"),
    SENT("SENT"),
    FAILED("FAILED");

    private final String value;


}
