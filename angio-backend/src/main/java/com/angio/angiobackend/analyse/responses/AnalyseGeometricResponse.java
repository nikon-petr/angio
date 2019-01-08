package com.angio.angiobackend.analyse.responses;

import com.angio.angiobackend.analyse.entities.AnalyseGeometricEntity;
import com.angio.angiobackend.analyse.entities.VesselEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnalyseGeometricResponse implements Serializable {
    private String originalImage;
    private String binarizedImage;
    private String skelImage;
    private List<VesselResponse> vessels;

    public AnalyseGeometricResponse(){
        this.vessels = new ArrayList<>();
    }

    public AnalyseGeometricResponse(String originalImage, String binarizedImage, String skelImage) {
        this.originalImage = originalImage;
        this.binarizedImage = binarizedImage;
        this.skelImage = skelImage;
        this.vessels = new ArrayList<>();
    }

    public AnalyseGeometricResponse(AnalyseGeometricEntity analyseGeometricEntity, List<VesselEntity> vessels, String originalImage){
        this.originalImage = originalImage;
        this.binarizedImage = analyseGeometricEntity.getBinarizedImage();
        this.skelImage = analyseGeometricEntity.getSkelImage();
        this.vessels = new ArrayList<>();
        for (VesselEntity vessel : vessels) this.vessels.add(new VesselResponse(vessel));
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public String getBinarizedImage() {
        return binarizedImage;
    }

    public void setBinarizedImage(String binarizedImage) {
        this.binarizedImage = binarizedImage;
    }

    public String getSkelImage() {
        return skelImage;
    }

    public void setSkelImage(String skelImage) {
        this.skelImage = skelImage;
    }

    public List<VesselResponse> getVessels() {
        return vessels;
    }

    public void setVessels(List<VesselResponse> vessels) {
        this.vessels = vessels;
    }
}