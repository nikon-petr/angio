package com.angio.server;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AngioApplication {

	private final Logger logger = LoggerFactory.getLogger(AngioApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AngioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		logger.info("It's started");
		return new ModelMapper();
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
