package org.example.exception;

/**
 * Thrown when Kafka sender is null or not initialized
 */
public class KafkaSenderNotInitializedException extends RuntimeException {
    public KafkaSenderNotInitializedException() {
        super("Kafka sender is not initialized.");
    }
}

