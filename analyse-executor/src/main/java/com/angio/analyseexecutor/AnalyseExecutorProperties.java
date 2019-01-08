package com.angio.analyseexecutor;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "analyse.executor.app")
public class AnalyseExecutorProperties {

    private String analyseImagesDirectory;

    public Jms jms;

    @Data
    public static class Jms {
        private String analyseToExecuteQueue;
        private String analyseResultsQueue;
    }
}
