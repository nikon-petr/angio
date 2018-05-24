package com.angio.server.analyse.responses;

import com.angio.server.analyse.entities.AnalyseInfoEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class DetailAnalyseInfoResponse implements Serializable {
    private long id;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String analyseType;
    private String comment;
    private String analyseDate;
    private String conclusion;

    public DetailAnalyseInfoResponse() {

    }

    public DetailAnalyseInfoResponse(long id, String name, String shortDescription, String fullDescription, String analyseType,
                                     String comment, String analyse_date, String conclusion) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.analyseType = analyseType;
        this.comment = comment;
        this.analyseDate = analyse_date;
        this.conclusion = conclusion;
    }

    public DetailAnalyseInfoResponse(AnalyseInfoEntity analyseInfoEntity) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        String date = sdf.format(analyseInfoEntity.getAnalyseDate());

        this.id = analyseInfoEntity.getId();
        this.name = analyseInfoEntity.getName();
        this.shortDescription = analyseInfoEntity.getShortDescription();
        this.fullDescription = analyseInfoEntity.getFullDescription();
        this.analyseType = analyseInfoEntity.getAnalyseType();
        this.comment = analyseInfoEntity.getComment();
        this.analyseDate = date;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getAnalyseType() {
        return analyseType;
    }

    public void setAnalyseType(String analyseType) {
        this.analyseType = analyseType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAnalyseDate() {
        return analyseDate;
    }

    public void setAnalyseDate(String analyseDate) {
        this.analyseDate = analyseDate;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}