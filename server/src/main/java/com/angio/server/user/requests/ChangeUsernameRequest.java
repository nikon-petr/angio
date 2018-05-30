package com.angio.server.user.requests;

import java.io.Serializable;

public class ChangeUsernameRequest implements Serializable {
    private String newEmail;

    public ChangeUsernameRequest() {
    }

    public ChangeUsernameRequest(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
