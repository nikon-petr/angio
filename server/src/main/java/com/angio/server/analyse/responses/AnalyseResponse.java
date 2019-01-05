package com.angio.server.analyse.responses;

import com.angio.server.analyse.entities.AnalyseInfoEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class AnalyseResponse implements Serializable {
    private long id;
    private String name;
    private String shortDescription;
    private String analyseType;
    private String patient;
    private String policy;
    private String user;
    private String analyseDate;
    private boolean analyseFinished;

    public AnalyseResponse() {

    }

    public AnalyseResponse(long id, String name, String shortDescription, String analyseType, String patient,
                           String policy, String user, String analyseDate, boolean analyseFinished) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.analyseType = analyseType;
        this.patient = patient;
        this.policy = policy;
        this.user = user;
        this.analyseDate = analyseDate;
        this.analyseFinished = analyseFinished;
    }

    public AnalyseResponse(AnalyseInfoEntity analyseInfoEntity, String user){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Samara"));
        String date = sdf.format(analyseInfoEntity.getAnalyseDate());

        this.id = analyseInfoEntity.getId();
        this.name = analyseInfoEntity.getName();
        this.shortDescription = analyseInfoEntity.getShortDescription();
        this.analyseType = analyseInfoEntity.getAnalyseType();
        this.patient = analyseInfoEntity.getPatient().getFullName().getFullNameString();
        this.policy = analyseInfoEntity.getPatient().getPolicy();
        this.user = user;
        this.analyseDate = date;
        this.analyseFinished = analyseInfoEntity.isFinished();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAnalyseType() {
        return analyseType;
    }

    public void setAnalyseType(String analyseType) {
        this.analyseType = analyseType;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAnalyseDate() {
        return analyseDate;
    }

    public void setAnalyseDate(String analyseDate) {
        this.analyseDate = analyseDate;
    }

    public boolean isAnalyseFinished() {
        return analyseFinished;
    }

    public void setAnalyseFinished(boolean analyseFinished) {
        this.analyseFinished = analyseFinished;
    }
}