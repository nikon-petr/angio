package com.angio.analyseexecutor.analyse.matlab.geometric.model;

import java.awt.image.BufferedImage;

public class VesselModel {
    private long id;
    private BufferedImage vesselImage;
    private BufferedImage mainVessel;
    private int countOfBranchesOf1Orders;
    private double tortuosity;
    private double branching;
    private double area;
    private double areaPercent;

    public VesselModel(){

    }

    public VesselModel(long id, BufferedImage vesselImage, BufferedImage mainVessel, int countOfBranchesOf1Orders,
                       double tortuosity, double branching, double area, double areaPercent) {
        this.id = id;
        this.vesselImage = vesselImage;
        this.mainVessel = mainVessel;
        this.countOfBranchesOf1Orders = countOfBranchesOf1Orders;
        this.tortuosity = tortuosity;
        this.branching = branching;
        this.area = area;
        this.areaPercent = areaPercent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BufferedImage getVesselImage() {
        return vesselImage;
    }

    public void setVesselImage(BufferedImage vesselImage) {
        this.vesselImage = vesselImage;
    }

    public BufferedImage getMainVessel() {
        return mainVessel;
    }

    public void setMainVessel(BufferedImage mainVessel) {
        this.mainVessel = mainVessel;
    }

    public int getCountOfBranchesOf1Orders() {
        return countOfBranchesOf1Orders;
    }

    public void setCountOfBranchesOf1Orders(int countOfBranchesOf1Orders) {
        this.countOfBranchesOf1Orders = countOfBranchesOf1Orders;
    }

    public double getTortuosity() {
        return tortuosity;
    }

    public void setTortuosity(double tortuosity) {
        this.tortuosity = tortuosity;
    }

    public double getBranching() {
        return branching;
    }

    public void setBranching(double branching) {
        this.branching = branching;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getAreaPercent() {
        return areaPercent;
    }

    public void setAreaPercent(double areaPercent) {
        this.areaPercent = areaPercent;
    }
}