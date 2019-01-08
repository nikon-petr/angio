package com.angio.analyseexecutor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

@Slf4j
@EnableJms
@SpringBootApplication
public class AnalyseExecutorApplication {

    public static void main(String[] args) throws InterruptedException {
        final ConfigurableApplicationContext context = SpringApplication.run(AnalyseExecutorApplication.class, args);
        log.info("It's started");
    }
}

