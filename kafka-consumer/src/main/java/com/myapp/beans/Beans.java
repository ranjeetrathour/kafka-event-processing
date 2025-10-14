package com.myapp.beans;

import com.myapp.mapper.EventMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    @Bean
    public EventMapper eventMapper() {
        return Mappers.getMapper(EventMapper.class);
    }

}
