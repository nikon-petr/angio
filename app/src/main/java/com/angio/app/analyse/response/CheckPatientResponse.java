package com.angio.app.analyse.response;

import java.io.Serializable;

public class CheckPatientResponse implements Serializable{
    private PatientResponse patient;
    private boolean isContains;

    public CheckPatientResponse(){

    }

    public CheckPatientResponse(PatientResponse patient, boolean isContains) {
        this.patient = patient;
        this.isContains = isContains;
    }

    public PatientResponse getPatient() {
        return patient;
    }

    public void setPatient(PatientResponse patient) {
        this.patient = patient;
    }

    public boolean isContains() {
        return isContains;
    }

    public void setContains(boolean contains) {
        isContains = contains;
    }
}