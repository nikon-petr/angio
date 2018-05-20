package com.angio.server.analyse.requests;

import java.io.Serializable;

public class AnalyseInfoRequest implements Serializable{
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String analyseType;
    private String comment;
    private String img;

    public AnalyseInfoRequest(){

    }

    public AnalyseInfoRequest(String name, String shortDescription, String fullDescription, String analyseType,
                              String comments, String img) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.analyseType = analyseType;
        this.comment = comments;
        this.img = img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}