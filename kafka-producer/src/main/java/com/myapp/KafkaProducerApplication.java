package com.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Complex Event Processing application.
 * Receiving event data (simulated or from external sources).
 * Save events to the database
 * Encrypting sensitive event data using symmetric cryptography,
 * Publishing events asynchronously to Kafka after successful database transaction,
 * Reusing common code via a shared service module,
 * Exposing REST APIs and providing interactive API documentation using Swagger/OpenAPI.
 * Once started, Swagger UI can be accessed at:
 * http://localhost:8081/swagger-ui/index.html
 */
@SpringBootApplication(scanBasePackages = {"com.example", "com.myapp"})
@EnableScheduling
public class KafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

}
