package com.angio.app.analyse.requests;

import java.io.Serializable;

public class AnalyseInfoIdRequest implements Serializable{
    private long id;

    public AnalyseInfoIdRequest(){

    }

    public AnalyseInfoIdRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}