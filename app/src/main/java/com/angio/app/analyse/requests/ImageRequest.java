package com.angio.app.analyse.requests;

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
}