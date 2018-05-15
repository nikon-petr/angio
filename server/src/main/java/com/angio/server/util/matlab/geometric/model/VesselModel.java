package com.angio.server.util.matlab.geometric.model;

import java.awt.image.BufferedImage;

public class VesselModel {
    private long id;
    private BufferedImage vessel_image;
    private BufferedImage main_vessel;
    private int count_of_branches_of_1_orders;
    private double tortuosity;
    private double branching;
    private double area;
    private double area_percent;

    public VesselModel(){

    }

    public VesselModel(long id, BufferedImage vessel_image, BufferedImage main_vessel, int count_of_branches_of_1_orders,
                       double tortuosity, double branching, double area, double area_percent) {
        this.id = id;
        this.vessel_image = vessel_image;
        this.main_vessel = main_vessel;
        this.count_of_branches_of_1_orders = count_of_branches_of_1_orders;
        this.tortuosity = tortuosity;
        this.branching = branching;
        this.area = area;
        this.area_percent = area_percent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BufferedImage getVessel_image() {
        return vessel_image;
    }

    public void setVessel_image(BufferedImage vessel_image) {
        this.vessel_image = vessel_image;
    }

    public BufferedImage getMain_vessel() {
        return main_vessel;
    }

    public void setMain_vessel(BufferedImage main_vessel) {
        this.main_vessel = main_vessel;
    }

    public int getCount_of_branches_of_1_orders() {
        return count_of_branches_of_1_orders;
    }

    public void setCount_of_branches_of_1_orders(int count_of_branches_of_1_orders) {
        this.count_of_branches_of_1_orders = count_of_branches_of_1_orders;
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

    public double getArea_percent() {
        return area_percent;
    }

    public void setArea_percent(double area_percent) {
        this.area_percent = area_percent;
    }
}