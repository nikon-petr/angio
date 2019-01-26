package com.angio.angiobackend.api.user.responses;

import java.io.Serializable;

public class UserExistsResponse implements Serializable{
    private final boolean exists;

    public UserExistsResponse(boolean status) {
        this.exists = status;
    }

    public boolean isExists() {
        return exists;
    }
}
