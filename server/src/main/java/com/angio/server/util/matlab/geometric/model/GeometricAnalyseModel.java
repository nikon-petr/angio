package com.angio.server.util.matlab.geometric.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GeometricAnalyseModel {
    private BufferedImage original;
    private BufferedImage binarized;
    private BufferedImage skel;
    private List<VesselModel> analyseResult;

    public GeometricAnalyseModel(){
        this.analyseResult = new ArrayList<>();
    }

    public GeometricAnalyseModel(BufferedImage original, BufferedImage binarized, BufferedImage skel){
        this.original = original;
        this.binarized = binarized;
        this.skel = skel;
        this.analyseResult = new ArrayList<>();
    }

    public BufferedImage getOriginal() {
        return original;
    }

    public void setOriginal(BufferedImage original) {
        this.original = original;
    }

    public BufferedImage getBinarized() {
        return binarized;
    }

    public void setBinarized(BufferedImage binarized) {
        this.binarized = binarized;
    }

    public BufferedImage getSkel() {
        return skel;
    }

    public void setSkel(BufferedImage skel) {
        this.skel = skel;
    }

    public List<VesselModel> getAnalyseResult() {
        return analyseResult;
    }

    public void setAnalyseResult(List<VesselModel> analyseResult) {
        this.analyseResult = analyseResult;
    }

    public void addAnalyseResult(VesselModel vesselModel){
        this.analyseResult.add(vesselModel);
    }
}