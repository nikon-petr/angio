package com.angio.angiobackend.api.analyse.responses;

import java.io.Serializable;

public class AnalyseInfoConclusionResponse implements Serializable{
    private String conclusion;

    public AnalyseInfoConclusionResponse(){

    }

    public AnalyseInfoConclusionResponse(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}