package com.angio.app.admin;

import java.util.Date;

public class AdminGreeting {

    private String content;
    private Date date;

    public AdminGreeting(String content, Date date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }
}