package com.angio.server.analyse.responses;

import com.angio.server.analyse.entities.AnalyseInfoEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class DetailAnalyseInfoResponse implements Serializable {
    private long id;
    private String name;
    private String short_description;
    private String full_description;
    private String analyse_type;
    private String comment;
    private String analyse_date;
    private String conclusion;

    public DetailAnalyseInfoResponse() {

    }

    public DetailAnalyseInfoResponse(long id, String name, String short_description, String full_description, String analyse_type,
                                     String comment, String analyse_date, String conclusion) {
        this.id = id;
        this.name = name;
        this.short_description = short_description;
        this.full_description = full_description;
        this.analyse_type = analyse_type;
        this.comment = comment;
        this.analyse_date = analyse_date;
        this.conclusion = conclusion;
    }

    public DetailAnalyseInfoResponse(AnalyseInfoEntity analyseInfoEntity) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String date = sdf.format(analyseInfoEntity.getAnalyse_date());

        this.id = analyseInfoEntity.getId();
        this.name = analyseInfoEntity.getName();
        this.short_description = analyseInfoEntity.getShort_description();
        this.full_description = analyseInfoEntity.getFull_description();
        this.analyse_type = analyseInfoEntity.getAnalyse_type();
        this.comment = analyseInfoEntity.getComment();
        this.analyse_date = date;
        this.conclusion = analyseInfoEntity.getConclusion();
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

    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    public String getAnalyse_type() {
        return analyse_type;
    }

    public void setAnalyse_type(String analyse_type) {
        this.analyse_type = analyse_type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAnalyse_date() {
        return analyse_date;
    }

    public void setAnalyse_date(String analyse_date) {
        this.analyse_date = analyse_date;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}