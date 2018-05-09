package com.angio.server.analyse.requests;

import java.io.Serializable;

public class AnalyseInfoRequest implements Serializable{
    private String name;
    private String short_description;
    private String full_description;
    private String analyse_type;
    private String comment;
    private String img;

    public AnalyseInfoRequest(){

    }

    public AnalyseInfoRequest(String name, String short_description, String full_description, String analyse_type,
                              String comments, String img) {
        this.name = name;
        this.short_description = short_description;
        this.full_description = full_description;
        this.analyse_type = analyse_type;
        this.comment = comments;
        this.img = img;
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

    public String getComments() {
        return comment;
    }

    public void setComments(String comments) {
        this.comment = comments;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}