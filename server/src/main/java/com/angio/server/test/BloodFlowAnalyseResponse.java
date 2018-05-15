package com.angio.server.test;

import com.angio.server.util.matlab.BloodFlowAnalyseResult;

import java.io.Serializable;

public class BloodFlowAnalyseResponse implements Serializable {

    private double[] ishemiaArea;
    private double[][] ishemiaCenter;
    private double makulaArea;
    private double[] makulaCenter;
    private double makulaRadius;
    private double[] capilarDensities;

    public BloodFlowAnalyseResponse(BloodFlowAnalyseResult bloodFlowAnalyseResult) {
        this.ishemiaArea = bloodFlowAnalyseResult.getIshemiaArea();
        this.ishemiaCenter = bloodFlowAnalyseResult.getIshemiaCenter();
        this.makulaArea = bloodFlowAnalyseResult.getMakulaArea();
        this.makulaCenter = bloodFlowAnalyseResult.getMakulaCenter();
        this.makulaRadius = bloodFlowAnalyseResult.getMakulaRadius();
        this.capilarDensities = bloodFlowAnalyseResult.getCapilarDensities();
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

    public double[] getCapilarDensities() {
        return capilarDensities;
    }

    public void setCapilarDensities(double[] capilarDensities) {
        this.capilarDensities = capilarDensities;
    }
}
