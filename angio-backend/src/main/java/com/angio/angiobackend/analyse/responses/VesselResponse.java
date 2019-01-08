package com.angio.angiobackend.analyse.responses;

import com.angio.angiobackend.analyse.entities.VesselEntity;

import java.io.Serializable;

public class VesselResponse implements Serializable {
    private long id;
    private String vesselImage;
    private String mainVesselImage;
    private float tortuosityDegree;
    private int countOfBranches;
    private float branchingDegree;
    private float area;
    private float areaPercent;

    public VesselResponse() {

    }

    public VesselResponse(long id, String vesselImage, String mainVesselImage, float tortuosityDegree,
                          int countOfBranches, float branchingDegree, float area, float areaPercent) {
        this.id = id;
        this.vesselImage = vesselImage;
        this.mainVesselImage = mainVesselImage;
        this.tortuosityDegree = tortuosityDegree;
        this.countOfBranches = countOfBranches;
        this.branchingDegree = branchingDegree;
        this.area = area;
        this.areaPercent = areaPercent;
    }

    public VesselResponse(VesselEntity vesselEntity) {
        this.id = vesselEntity.getId();
        this.vesselImage = vesselEntity.getVesselImage();
        this.mainVesselImage = vesselEntity.getMainVesselImage();
        this.tortuosityDegree = vesselEntity.getTortuosityDegree();
        this.countOfBranches = vesselEntity.getCountOfBranches();
        this.branchingDegree = vesselEntity.getBranchingDegree();
        this.area = vesselEntity.getArea();
        this.areaPercent = vesselEntity.getAreaPercent();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVesselImage() {
        return vesselImage;
    }

    public void setVesselImage(String vesselImage) {
        this.vesselImage = vesselImage;
    }

    public String getMainVesselImage() {
        return mainVesselImage;
    }

    public void setMainVesselImage(String mainVesselImage) {
        this.mainVesselImage = mainVesselImage;
    }

    public float getTortuosityDegree() {
        return tortuosityDegree;
    }

    public void setTortuosityDegree(float tortuosityDegree) {
        this.tortuosityDegree = tortuosityDegree;
    }

    public int getCountOfBranches() {
        return countOfBranches;
    }

    public void setCountOfBranches(int countOfBranches) {
        this.countOfBranches = countOfBranches;
    }

    public float getBranchingDegree() {
        return branchingDegree;
    }

    public void setBranchingDegree(float branchingDegree) {
        this.branchingDegree = branchingDegree;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getAreaPercent() {
        return areaPercent;
    }

    public void setAreaPercent(float areaPercent) {
        this.areaPercent = areaPercent;
    }
}