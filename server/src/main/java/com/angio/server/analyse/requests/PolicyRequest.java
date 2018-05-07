package com.angio.server.analyse.requests;

import java.io.Serializable;

public class PolicyRequest implements Serializable {
    private String policy;

    public PolicyRequest(){

    }

    public PolicyRequest(String policy) {
        this.policy = policy;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}