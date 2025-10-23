package com.myapp.service;

import com.myapp.propertis.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.example.crypto.CryptoService;
import org.example.dto.ComplexEventDto;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaLatestRecordService {

    private final KafkaProperties kafkaProperties;
    private final ConsumerFactory<String, ComplexEventDto> consumerFactory;
    private final CryptoService cryptoService;


    private String lastFetchedMessageId = null;
    private long lastFetchedTimestamp = 0;

    /**
     * This method fetches the latest record from the given Kafka topic.
     * We use ConsumerFactory so that we can reuse the existing Spring Kafka configuration
     * (like bootstrap servers, group id, deserializers) instead of creating new properties each time.
     * seekToEnd() moves the cursor directly to the last offset, so we always get the latest message
     * without scanning the entire partition. This is also better for performance, especially for large topics.
     * After fetching the record, we decrypt the payload and metadata to get the original data back.
     */
    public Optional<ComplexEventDto> fetchLatestRecord() {
        try (var consumer = consumerFactory.createConsumer()) {
            TopicPartition partition = new TopicPartition(kafkaProperties.getTopic(), kafkaProperties.getPartition());
            consumer.assign(Collections.singletonList(partition));
            consumer.seekToEnd(Collections.singletonList(partition));
            long endOffset = consumer.position(partition);

            if (endOffset == 0) {
                log.info("No messages in topic '{}'", kafkaProperties.getTopic());
                return Optional.of(createEmptyDto("No messages found in topic"));
            }
            consumer.seek(partition, endOffset - 1);
            var records = consumer.poll(Duration.ofMillis(200));
            if (records.isEmpty()) {
                return Optional.of(createEmptyDto("No records returned from poll"));
            }
            var record = records.records(partition).stream().findFirst().get();
            ComplexEventDto dto = record.value();
            dto.setPayloadJson(cryptoService.decrypt(dto.getPayloadJson()));
            dto.setMetadataJson(cryptoService.decrypt(dto.getMetadataJson()));

            if (dto.getId() != null && dto.getId().equals(lastFetchedMessageId)) {
                log.info("No new message since last fetch (same message ID)");
                return Optional.of(createEmptyDto("No new message since last fetch"));
            }
            lastFetchedMessageId = dto.getId();
            lastFetchedTimestamp = System.currentTimeMillis();

            return Optional.of(dto);

        } catch (Exception e) {
            log.error("Failed to fetch latest Kafka record", e);
            return Optional.of(createEmptyDto("Kafka is not available or error occurred"));
        }
    }

    /**
     * Creates a dummy ComplexEventDto with a given message.
     * This is used when Kafka is not available or there are no messages.
     * We return this so that the API can always return something meaningful
     * instead of failing or waiting indefinitely.
     *
     * @param message the message to include in the dummy DTO
     * @return a ComplexEventDto with ID "N/A" and the given message in payload & metadata
     */
    private ComplexEventDto createEmptyDto(String message) {
        ComplexEventDto dto = new ComplexEventDto();
        dto.setId("N/A");
        dto.setPayloadJson(message);
        dto.setMetadataJson(message);
        return dto;
    }


}
