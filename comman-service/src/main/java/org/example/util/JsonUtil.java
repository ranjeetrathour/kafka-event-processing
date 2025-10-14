package org.example.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for JSON serialization.
 * This class is declared final because:
 * - It should not be extended or modified through inheritance.
 * - It contains only static utility methods.
 * <p>
 * The constructor is private to prevent object creation.
 * The ObjectMapper is static and final to reuse a single instance efficiently.
 */
public final class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    public static String toJson(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize object", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize JSON", e);
        }
    }
}

