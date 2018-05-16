package com.angio.server.analyse.responses;

import java.io.Serializable;

public class CheckPatientResponse implements Serializable{
    private PatientResponse patient;
    private boolean exists;

    public CheckPatientResponse(){

    }

    public CheckPatientResponse(PatientResponse patient, boolean exists) {
        this.patient = patient;
        this.exists = exists;
    }

    public PatientResponse getPatient() {
        return patient;
    }

    public void setPatient(PatientResponse patient) {
        this.patient = patient;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}