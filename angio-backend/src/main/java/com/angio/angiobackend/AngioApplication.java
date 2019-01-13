package com.angio.angiobackend;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

@Slf4j
@AllArgsConstructor
@SpringBootApplication
public class AngioApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		log.info("It's started");
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
