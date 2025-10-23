package com.myapp.constant;

/**
 * A utility class that holds all constant values used across the application.
 * This class contains application-wise static final constants:
 * for-:
 * Error messages
 * User-friendly messages
 * any other if needed
 */
public class Constants {
    public static final String EVENT_ENTITY_CAN_NOT_BE_NULL = "Event entity can not be null";
    public static final String FAILED_TO_SEND_KAFKA_EVENT = "Failed to send ComplexEvent DTO to Kafka for event id";
    public static final String RECEIVED_EVENT_CAN_NOT_BE_NULL = "Received null ComplexEvent";
}
