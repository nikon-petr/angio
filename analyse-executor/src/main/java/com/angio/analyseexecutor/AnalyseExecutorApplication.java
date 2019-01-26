package com.angio.analyseexecutor;

import com.angio.analyseexecutor.analyse.dto.AnalyseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Collections;

@Slf4j
@EnableJms
@SpringBootApplication
public class AnalyseExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyseExecutorApplication.class, args);
        log.info("It's alive!!!");
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setTypeIdMappings(Collections.singletonMap("_type", AnalyseDto.class));
        return converter;
    }
}

