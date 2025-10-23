package com.myapp.service;


import com.myapp.domain.ComplexEvent;
import com.myapp.mapper.EventMapper;
import com.myapp.repository.ComplexEventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crypto.CryptoService;
import org.example.dto.ComplexEventDto;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
@Slf4j
public class ComplexEventConsumerService {

    private final ComplexEventRepository repository;
    private final EventMapper eventMapper;
    private final CryptoService cryptoService;


    public void processEvent(ComplexEventDto dto) {
        String decryptedPayload = cryptoService.decrypt(dto.getPayloadJson());
        String decryptedMetadata = cryptoService.decrypt(dto.getMetadataJson());
        dto.setPayloadJson(decryptedPayload);
        dto.setMetadataJson(decryptedMetadata);
        repository.save(eventMapper.toComplexEvent(dto));
        log.info("Consumed & saved event id=" + dto.getId());
    }

    public List<ComplexEvent> getAllData() {
        return repository.getAllData();
    }
}

