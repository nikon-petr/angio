package com.angio.angiobackend;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties
public class AngioApplication {

	public static final List<String> SUPPORTED_LOCALES = Arrays.asList("en-US", "ru-RU");

	public static void main(String[] args) {
		SpringApplication.run(AngioApplication.class, args);
		log.info("It's alive!!!");
	}
}
