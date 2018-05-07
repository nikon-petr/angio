package com.angio.server.security.responses;

import java.io.Serializable;

public class CheckUsernameResponse implements Serializable{
    private final String status;

    public CheckUsernameResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
