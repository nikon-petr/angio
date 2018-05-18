package com.angio.server.analyse.responses;

import java.io.Serializable;

public class IdResponse implements Serializable {
    private long id;

    public IdResponse(){

    }

    public IdResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}