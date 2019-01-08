package com.angio.angiobackend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "angio.app")
public class AngioAppProperties {

    private String analyseImagesDirectory;

    public Jwt jwt;

    public static class Jwt {

        private String header;
        private String tokenType;
        private String secret;
        private Long expiration;

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public Long getExpiration() {
            return expiration;
        }

        public void setExpiration(Long expiration) {
            this.expiration = expiration;
        }
    }

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public String getAnalyseImagesDirectory() {
        return analyseImagesDirectory;
    }

    public void setAnalyseImagesDirectory(String analyseImagesDirectory) {
        this.analyseImagesDirectory = analyseImagesDirectory;
    }
}
