package com.angio.app.analyse.responses;

import com.angio.app.analyse.entities.VesselEntity;

import java.io.Serializable;

public class VesselResponse implements Serializable {
    private long id;
    private String vessel_image;
    private String main_vessel_image;
    private float tortuosity_degree;
    private int count_of_branches;
    private float branching_degree;
    private float area;
    private float area_percent;

    public VesselResponse() {

    }

    public VesselResponse(long id, String vessel_image, String main_vessel_image, float tortuosity_degree,
                          int count_of_branches, float branching_degree, float area, float area_percent) {
        this.id = id;
        this.vessel_image = vessel_image;
        this.main_vessel_image = main_vessel_image;
        this.tortuosity_degree = tortuosity_degree;
        this.count_of_branches = count_of_branches;
        this.branching_degree = branching_degree;
        this.area = area;
        this.area_percent = area_percent;
    }

    public VesselResponse(VesselEntity vesselEntity) {
        this.id = vesselEntity.getId();
        this.vessel_image = vesselEntity.getVessel_image();
        this.main_vessel_image = vesselEntity.getMain_vessel_image();
        this.tortuosity_degree = vesselEntity.getTortuosity_degree();
        this.count_of_branches = vesselEntity.getCount_of_branches();
        this.branching_degree = vesselEntity.getBranching_degree();
        this.area = vesselEntity.getArea();
        this.area_percent = vesselEntity.getArea();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVessel_image() {
        return vessel_image;
    }

    public void setVessel_image(String vessel_image) {
        this.vessel_image = vessel_image;
    }

    public String getMain_vessel_image() {
        return main_vessel_image;
    }

    public void setMain_vessel_image(String main_vessel_image) {
        this.main_vessel_image = main_vessel_image;
    }

    public float getTortuosity_degree() {
        return tortuosity_degree;
    }

    public void setTortuosity_degree(float tortuosity_degree) {
        this.tortuosity_degree = tortuosity_degree;
    }

    public int getCount_of_branches() {
        return count_of_branches;
    }

    public void setCount_of_branches(int count_of_branches) {
        this.count_of_branches = count_of_branches;
    }

    public float getBranching_degree() {
        return branching_degree;
    }

    public void setBranching_degree(float branching_degree) {
        this.branching_degree = branching_degree;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getArea_percent() {
        return area_percent;
    }

    public void setArea_percent(float area_percent) {
        this.area_percent = area_percent;
    }
}