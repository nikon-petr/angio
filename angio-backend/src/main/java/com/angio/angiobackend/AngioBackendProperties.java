package com.angio.angiobackend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "angiobackend.app")
public class AngioBackendProperties {

    private String baseUrl;
    private String uploadPath;
    private String frontendDistPath;

    private String sentEmailDirectory;
    private String uploadDirectory;
    private String frontendDistDirectory;

    public Ui ui;

    private String[] imageUploadExtensions;

    private String[] documentUploadExtensions;

    public Jms jms;

    public Scheduling scheduling;

    public UserDefaultSettings userDefaultSettings;

    @Data
    public static class Jms {
        private String analyseToExecuteQueue;
        private String analyseResultsQueue;
    }

    @Data
    public static class Scheduling {
        private String purgeAnalysesPeriod;
        private String purgeImagesPeriod;
    }

    @Data
    public static class UserDefaultSettings {
        private Boolean darkThemeEnabled;
    }

    @Data
    public static class Ui {
        private String userActivationFormPath;
        private String userResettingFormPath;
    }
}
