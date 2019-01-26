package com.angio.angiobackend.api.analyse.requests;

import java.io.Serializable;

public class UpdateAnalyseInfoConclusionRequest implements Serializable {
    private long id;
    private String conclusion;

    public UpdateAnalyseInfoConclusionRequest(){

    }

    public UpdateAnalyseInfoConclusionRequest(long id, String conclusion) {
        this.id = id;
        this.conclusion = conclusion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}