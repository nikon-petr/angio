package com.angio.server.analyse.responses;

import com.angio.server.analyse.entities.AnalyseGeometricEntity;
import com.angio.server.analyse.entities.VesselEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnalyseGeometricResponse implements Serializable {
    private String original_image;
    private String binarized_image;
    private String skel_image;
    private List<VesselResponse> vessels;

    public AnalyseGeometricResponse(){
        this.vessels = new ArrayList<>();
    }

    public AnalyseGeometricResponse(String original_image, String binarized_image, String skel_image) {
        this.original_image = original_image;
        this.binarized_image = binarized_image;
        this.skel_image = skel_image;
        this.vessels = new ArrayList<>();
    }

    public AnalyseGeometricResponse(AnalyseGeometricEntity analyseGeometricEntity, List<VesselEntity> vessels){
        this.original_image = analyseGeometricEntity.getOriginal_image();
        this.binarized_image = analyseGeometricEntity.getBinarized_image();
        this.skel_image = analyseGeometricEntity.getSkel_image();
        this.vessels = new ArrayList<>();
        for (VesselEntity vessel : vessels) this.vessels.add(new VesselResponse(vessel));
    }

    public String getOriginal_image() {
        return original_image;
    }

    public void setOriginal_image(String original_image) {
        this.original_image = original_image;
    }

    public String getBinarized_image() {
        return binarized_image;
    }

    public void setBinarized_image(String binarized_image) {
        this.binarized_image = binarized_image;
    }

    public String getSkel_image() {
        return skel_image;
    }

    public void setSkel_image(String skel_image) {
        this.skel_image = skel_image;
    }

    public List<VesselResponse> getVessels() {
        return vessels;
    }

    public void setVessels(List<VesselResponse> vessels) {
        this.vessels = vessels;
    }
}