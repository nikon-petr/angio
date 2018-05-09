package com.angio.app.analyse.responses;

import com.angio.app.analyse.entities.AnalyseGeometricEntity;
import com.angio.app.analyse.entities.AnalyseInfoEntity;
import com.angio.app.analyse.entities.PatientEntity;
import com.angio.app.analyse.entities.VesselEntity;

import java.io.Serializable;
import java.util.List;

public class DetailAnalyseResponse implements Serializable {
    private PatientResponse patient;
    private DetailAnalyseInfoResponse info;
    private AnalyseGeometricResponse geometric_analyse;
    private String username;

    public DetailAnalyseResponse() {

    }

    public DetailAnalyseResponse(PatientEntity patientEntity, AnalyseInfoEntity analyseInfoEntity,
                                 AnalyseGeometricEntity analyseGeometricEntity, List<VesselEntity> vesselsEntity, String username) {
        this.patient = new PatientResponse(patientEntity);
        this.info = new DetailAnalyseInfoResponse(analyseInfoEntity);
        this.geometric_analyse = new AnalyseGeometricResponse(analyseGeometricEntity, vesselsEntity);
        this.username = username;
    }

    public PatientResponse getPatient() {
        return patient;
    }

    public void setPatient(PatientResponse patient) {
        this.patient = patient;
    }

    public DetailAnalyseInfoResponse getInfo() {
        return info;
    }

    public void setInfo(DetailAnalyseInfoResponse info) {
        this.info = info;
    }

    public AnalyseGeometricResponse getGeometric_analyse() {
        return geometric_analyse;
    }

    public void setGeometric_analyse(AnalyseGeometricResponse geometric_analyse) {
        this.geometric_analyse = geometric_analyse;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}