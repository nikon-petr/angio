package com.angio.server.security.requests;

import java.io.Serializable;

public class RevokeTokenRequest implements Serializable {
    private long id;

    public RevokeTokenRequest() {
        super();
    }

    public RevokeTokenRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
