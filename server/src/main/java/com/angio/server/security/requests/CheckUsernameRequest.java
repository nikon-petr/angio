package com.angio.server.security.requests;

import java.io.Serializable;

public class CheckUsernameRequest implements Serializable {
    private String username;

    public CheckUsernameRequest() {
    }

    public CheckUsernameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
