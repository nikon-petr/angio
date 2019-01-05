package com.angio.server.analyse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vessels", schema = "public")
public class VesselEntity {
    private long id;
    private AnalyseGeometricEntity analyseGeometric;
    private String vesselImage;
    private String mainVesselImage;
    private float tortuosityDegree;
    private int countOfBranches;
    private float branchingDegree;
    private float area;
    private float areaPercent;

    public VesselEntity(){

    }

    public VesselEntity(AnalyseGeometricEntity analyseGeometric, String vesselImage, String mainVesselImage,
                        float tortuosityDegree, int countOfBranches, float branchingDegree,
                        float area, float areaPercent) {
        this.analyseGeometric = analyseGeometric;
        this.vesselImage = vesselImage;
        this.mainVesselImage = mainVesselImage;
        this.tortuosityDegree = tortuosityDegree;
        this.countOfBranches = countOfBranches;
        this.branchingDegree = branchingDegree;
        this.area = area;
        this.areaPercent = areaPercent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analyse_geometric_id", nullable = false)
    public AnalyseGeometricEntity getAnalyseGeometric() {
        return analyseGeometric;
    }

    public void setAnalyseGeometric(AnalyseGeometricEntity analyseGeometric) {
        this.analyseGeometric = analyseGeometric;
    }

    @Column(name = "vessel_image", nullable = false, length = 400)
    public String getVesselImage() {
        return vesselImage;
    }

    public void setVesselImage(String vesselImage) {
        this.vesselImage = vesselImage;
    }

    @Column(name = "main_vessel_image", nullable = false, length = 400)
    public String getMainVesselImage() {
        return mainVesselImage;
    }

    public void setMainVesselImage(String mainVesselImage) {
        this.mainVesselImage = mainVesselImage;
    }

    @Column(name = "tortuosity_degree", nullable = false)
    public float getTortuosityDegree() {
        return tortuosityDegree;
    }

    public void setTortuosityDegree(float tortuosityDegree) {
        this.tortuosityDegree = tortuosityDegree;
    }

    @Column(name = "count_of_branches", nullable = false)
    public int getCountOfBranches() {
        return countOfBranches;
    }

    public void setCountOfBranches(int countOfBranches) {
        this.countOfBranches = countOfBranches;
    }

    @Column(name = "branching_degree", nullable = false)
    public float getBranchingDegree() {
        return branchingDegree;
    }

    public void setBranchingDegree(float branchingDegree) {
        this.branchingDegree = branchingDegree;
    }

    @Column(name = "area", nullable = false)
    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    @Column(name = "area_percent", nullable = false)
    public float getAreaPercent() {
        return areaPercent;
    }

    public void setAreaPercent(float areaPercent) {
        this.areaPercent = areaPercent;
    }
}