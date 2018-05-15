package com.angio.server.util.matlab.geometric.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GeometricAnalyseModel {
    private BufferedImage original;
    private BufferedImage binarized;
    private BufferedImage skel;
    private List<VesselModel> analyse_result;

    public GeometricAnalyseModel(){
        this.analyse_result = new ArrayList<>();
    }

    public GeometricAnalyseModel(BufferedImage original, BufferedImage binarized, BufferedImage skel){
        this.original = original;
        this.binarized = binarized;
        this.skel = skel;
        this.analyse_result = new ArrayList<>();
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

    public List<VesselModel> getAnalyse_result() {
        return analyse_result;
    }

    public void setAnalyse_result(List<VesselModel> analyse_result) {
        this.analyse_result = analyse_result;
    }

    public void addAnalyse_result(VesselModel vesselModel){
        this.analyse_result.add(vesselModel);
    }
}