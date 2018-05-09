package com.angio.app.analyse.requests;

import java.io.Serializable;

public class IdRequest implements Serializable{
    private long id;

    public IdRequest(){

    }

    public IdRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}