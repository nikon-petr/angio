package com.angio.angiobackend;

import com.angio.angiobackend.api.analyse.dto.AnalyseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Collections;

@Slf4j
@AllArgsConstructor
@SpringBootApplication
public class AngioApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		log.info("It's alive!!!");
		return new ModelMapper();
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		converter.setTypeIdMappings(Collections.singletonMap("_type", AnalyseDto.class));
		return converter;
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins("*")
//				.allowedMethods();
//			}
//		};
//	}
}
