package com.angio.server.analyse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "vessels", catalog = "public")
public class VesselEntity {
    private long id;
    private AnalyseGeometricEntity analyseGeometric;
    private String vessel_image;
    private String main_vessel_image;
    private float tortuosity_degree;
    private int count_of_branches;
    private float branching_degree;
    private float area;
    private float area_percent;

    public VesselEntity(){

    }

    public VesselEntity(AnalyseGeometricEntity analyseGeometric, String vessel_image, String main_vessel_image,
                        float tortuosity_degree, int count_of_branches, float branching_degree,
                        float area, float area_percent) {
        this.analyseGeometric = analyseGeometric;
        this.vessel_image = vessel_image;
        this.main_vessel_image = main_vessel_image;
        this.tortuosity_degree = tortuosity_degree;
        this.count_of_branches = count_of_branches;
        this.branching_degree = branching_degree;
        this.area = area;
        this.area_percent = area_percent;
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
    public String getVessel_image() {
        return vessel_image;
    }

    public void setVessel_image(String vessel_image) {
        this.vessel_image = vessel_image;
    }

    @Column(name = "main_vessel_image", nullable = false, length = 400)
    public String getMain_vessel_image() {
        return main_vessel_image;
    }

    public void setMain_vessel_image(String main_vessel_image) {
        this.main_vessel_image = main_vessel_image;
    }

    @Column(name = "tortuosity_degree", nullable = false)
    public float getTortuosity_degree() {
        return tortuosity_degree;
    }

    public void setTortuosity_degree(float tortuosity_degree) {
        this.tortuosity_degree = tortuosity_degree;
    }

    @Column(name = "count_of_branches", nullable = false)
    public int getCount_of_branches() {
        return count_of_branches;
    }

    public void setCount_of_branches(int count_of_branches) {
        this.count_of_branches = count_of_branches;
    }

    @Column(name = "branching_degree", nullable = false)
    public float getBranching_degree() {
        return branching_degree;
    }

    public void setBranching_degree(float branching_degree) {
        this.branching_degree = branching_degree;
    }

    @Column(name = "area", nullable = false)
    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    @Column(name = "area_percent", nullable = false)
    public float getArea_percent() {
        return area_percent;
    }

    public void setArea_percent(float area_percent) {
        this.area_percent = area_percent;
    }
}