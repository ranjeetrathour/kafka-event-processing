package com.myapp.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventMetadata {

    @Column(name = "source_system")
    private String sourceSystem;

    @Column(name = "source_version")
    private String sourceVersion;

    @Column(name = "origin_ip")
    private String originIp;

}

