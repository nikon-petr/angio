package com.angio.angiobackend.analyse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "makulas", schema = "public")
public class MakulaEntity {

    private long id;
    private AnalyseBloodFlowEntity analyseBloodFlow;
    private double area;
    private double radius;
    private double x;
    private double y;

    public MakulaEntity() {
    }

    public MakulaEntity(AnalyseBloodFlowEntity analyseBloodFlow, double area, double radius, double x, double y) {
        this.analyseBloodFlow = analyseBloodFlow;
        this.area = area;
        this.radius = radius;
        this.x = x;
        this.y = y;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analyse_bloodflow_id", nullable = false)
    public AnalyseBloodFlowEntity getAnalyseBloodFlow() {
        return analyseBloodFlow;
    }

    public void setAnalyseBloodFlow(AnalyseBloodFlowEntity analyseBloodFlow) {
        this.analyseBloodFlow = analyseBloodFlow;
    }

    @Column(name = "area", nullable = false)
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Column(name = "radius", nullable = false)
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Column(name = "x", nullable = false)
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Column(name = "y", nullable = false)
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
