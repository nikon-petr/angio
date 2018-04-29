package com.angio.app.security.responses;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private final String token;
    private final String type;

    public AuthenticationResponse(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return type;
    }


}
