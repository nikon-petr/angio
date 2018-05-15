package com.angio.server.analyse.requests;

import java.io.Serializable;

public class NewAnalyseRequest implements Serializable {
    private PatientRequest patient;
    private AnalyseInfoRequest info;

    public NewAnalyseRequest(){

    }

    public NewAnalyseRequest(PatientRequest patient, AnalyseInfoRequest info, String username) {
        this.patient = patient;
        this.info = info;
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
}