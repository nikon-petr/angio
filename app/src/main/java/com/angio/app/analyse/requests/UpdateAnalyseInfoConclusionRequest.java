package com.angio.app.analyse.requests;

import com.angio.app.analyse.entities.PatientEntity;
import com.angio.app.security.entities.UserEntity;

import java.io.Serializable;
import java.util.Date;

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