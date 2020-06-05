package com.angio.analyseexecutor;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "analyseexecutor.app")
public class AnalyseExecutorProperties {

    private String uploadDirectory;
    private String resultImageFormat;

    public Jms jms;

    @Data
    public static class Jms {
        private String analyseExecuteRequest;
        private String analyseExecuteResponse;
    }
}
