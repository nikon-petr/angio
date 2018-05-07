package com.angio.server.analyse.responses;

import com.angio.server.analyse.entities.AnalyseInfoEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class AnalyseResponse implements Serializable {
    private long id;
    private String name;
    private String short_description;
    private String analyse_type;
    private String patient;
    private String policy;
    private String diagnost;
    private String date;
    private boolean is_analyse_finished;

    public AnalyseResponse() {

    }

    public AnalyseResponse(long id, String name, String short_description, String analyse_type, String patient,
                           String policy, String diagnost, String date, boolean is_analyse_finished) {
        this.id = id;
        this.name = name;
        this.short_description = short_description;
        this.analyse_type = analyse_type;
        this.patient = patient;
        this.policy = policy;
        this.diagnost = diagnost;
        this.date = date;
        this.is_analyse_finished = is_analyse_finished;
    }

    public AnalyseResponse(AnalyseInfoEntity analyseInfoEntity, String diagnost){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(analyseInfoEntity.getAnalyse_date());

        this.id = analyseInfoEntity.getId();
        this.name = analyseInfoEntity.getName();
        this.short_description = analyseInfoEntity.getShort_description();
        this.analyse_type = analyseInfoEntity.getAnalyse_type();
        this.patient = analyseInfoEntity.getPatient().getLastname() + " " +
                analyseInfoEntity.getPatient().getFirstname() + " " +
                analyseInfoEntity.getPatient().getPatronymic();
        this.policy = analyseInfoEntity.getPatient().getPolicy();
        this.diagnost = diagnost;
        this.date = date;
        this.is_analyse_finished = analyseInfoEntity.isFinished();
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

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getAnalyse_type() {
        return analyse_type;
    }

    public void setAnalyse_type(String analyse_type) {
        this.analyse_type = analyse_type;
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

    public String getDiagnost() {
        return diagnost;
    }

    public void setDiagnost(String diagnost) {
        this.diagnost = diagnost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean is_analyse_finished() {
        return is_analyse_finished;
    }

    public void setIs_analyse_finished(boolean is_analyse_finished) {
        this.is_analyse_finished = is_analyse_finished;
    }
}