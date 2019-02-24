package com.angio.angiobackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
        basePackages = "com.angio.angiobackend"
)
public class PersistenceConfig {
}
