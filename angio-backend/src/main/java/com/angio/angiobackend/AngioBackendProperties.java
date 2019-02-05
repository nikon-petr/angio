package com.angio.angiobackend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "angio.app")
public class AngioBackendProperties {

    private String uploadDirectory;
    private String uploadPath;
    private String[] imageUploadExtensions;
    private String[] documentUploadExtensions;
    public Jwt jwt;
    public Jms jms;
    public Scheduling scheduling;

    @Data
    public static class Jwt {
        private String header;
        private String tokenType;
        private String secret;
        private Long expiration;
    }

    @Data
    public static class Jms {
        private String analyseToExecuteQueue;
        private String analyseResultsQueue;
    }

    @Data
    public static class Scheduling {
        private String purgeAnalysesPeriod;
    }
}
