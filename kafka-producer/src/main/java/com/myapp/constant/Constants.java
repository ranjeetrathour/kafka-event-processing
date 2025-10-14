package com.myapp.constant;

public class Constants {

    public static final String KAFKA_SENDER_NULL = "Kafka sender is not initialized.";
    public static final String KAFKA_SEND_FAILED = "Failed to send event to Kafka topic.";

    public static final String EVENT_MAPPING_FAILED = "Failed to map EventRequest to ComplexEvent.";
    public static final String EVENT_DTO_CREATION_FAILED = "Failed to create ComplexEventDto.";

    public static final String EVENT_SAVE_FAILED = "Failed to save ComplexEvent to the database.";
    public static final String EVENT_NOT_FOUND = "Event not found.";

    public static final String EVENT_PROCESS_FAILED = "Failed to process received event.";

    public static final String EVENT_ENTITY_CAN_NOT_BE_NULL = "Event entity can not be null";
    public static final String FAILED_TO_SEND_KAFKA_EVENT ="Failed to send ComplexEvent DTO to Kafka for event id";

}
