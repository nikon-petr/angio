package com.angio.server.analyse.requests;

import java.io.Serializable;

public class ImageRequest implements Serializable {
    private String image;

    public ImageRequest(){

    }

    public ImageRequest(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}