package com.angio.angiobackend.util.matlab.bloodflow;

import java.awt.image.BufferedImage;

public class BloodFlowAnalyseResult {

    private double[] ishemiaArea;
    private double[][] ishemiaCenter;
    private BufferedImage ishemiaImage;
    private double makulaArea;
    private double[] makulaCenter;
    private double makulaRadius;
    private BufferedImage capillarDensityImage;
    private double[] capilarDensities;

    public BloodFlowAnalyseResult(
            double[] ishemiaArea,
            double[][] ishemiaCenter,
            BufferedImage ishemiaImage,
            double makulaArea,
            double[] makulaCenter,
            double makulaRadius,
            BufferedImage capillarDensityImage,
            double[] capilarDensities) {
        this.ishemiaArea = ishemiaArea;
        this.ishemiaCenter = ishemiaCenter;
        this.ishemiaImage = ishemiaImage;
        this.makulaArea = makulaArea;
        this.makulaCenter = makulaCenter;
        this.makulaRadius = makulaRadius;
        this.capillarDensityImage = capillarDensityImage;
        this.capilarDensities = capilarDensities;
    }

    public double[] getIshemiaArea() {
        return ishemiaArea;
    }

    public void setIshemiaArea(double[] ishemiaArea) {
        this.ishemiaArea = ishemiaArea;
    }

    public double[][] getIshemiaCenter() {
        return ishemiaCenter;
    }

    public void setIshemiaCenter(double[][] ishemiaCenter) {
        this.ishemiaCenter = ishemiaCenter;
    }

    public BufferedImage getIshemiaImage() {
        return ishemiaImage;
    }

    public void setIshemiaImage(BufferedImage ishemiaImage) {
        this.ishemiaImage = ishemiaImage;
    }

    public double getMakulaArea() {
        return makulaArea;
    }

    public void setMakulaArea(double makulaArea) {
        this.makulaArea = makulaArea;
    }

    public double[] getMakulaCenter() {
        return makulaCenter;
    }

    public void setMakulaCenter(double[] makulaCenter) {
        this.makulaCenter = makulaCenter;
    }

    public double getMakulaRadius() {
        return makulaRadius;
    }

    public void setMakulaRadius(double makulaRadius) {
        this.makulaRadius = makulaRadius;
    }

    public BufferedImage getCapillarDensityImage() {
        return capillarDensityImage;
    }

    public void setCapillarDensityImage(BufferedImage capillarDensityImage) {
        this.capillarDensityImage = capillarDensityImage;
    }

    public double[] getCapilarDensities() {
        return capilarDensities;
    }

    public void setCapilarDensities(double[] capilarDensities) {
        this.capilarDensities = capilarDensities;
    }
}
