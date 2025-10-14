package org.example.exception;

import org.springframework.http.HttpStatus;

public class KafkaSendFailedException extends RuntimeException {

    private final int statusCode;

    // Constructor with message only, default status code 500
    public KafkaSendFailedException(String message) {
        super(message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    // Constructor with message and cause, default status code 500
    public KafkaSendFailedException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    // Constructor with custom status code
    public KafkaSendFailedException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    // Constructor with custom status code and cause
    public KafkaSendFailedException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
