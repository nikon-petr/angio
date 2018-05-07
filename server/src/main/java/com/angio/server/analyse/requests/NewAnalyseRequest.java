package com.angio.server.analyse.requests;

import java.io.Serializable;

public class NewAnalyseRequest implements Serializable {
    private PatientRequest patient;
    private AnalyseInfoRequest info;
    private String username;

    public NewAnalyseRequest(){

    }

    public NewAnalyseRequest(PatientRequest patient, AnalyseInfoRequest info, String username) {
        this.patient = patient;
        this.info = info;
        this.username = username;
    }

    public PatientRequest getPatient() {
        return patient;
    }

    public void setPatient(PatientRequest patient) {
        this.patient = patient;
    }

    public AnalyseInfoRequest getInfo() {
        return info;
    }

    public void setInfo(AnalyseInfoRequest info) {
        this.info = info;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}