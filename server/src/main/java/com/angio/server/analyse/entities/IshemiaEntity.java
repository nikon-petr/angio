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
@Table(name = "ischemias", schema = "public")
public class IshemiaEntity {

    private long id;
    private AnalyseBloodFlowEntity analyseBloodFlow;
    private double area;
    private int zoneNumber;
    private double x;
    private double y;

    public IshemiaEntity() {
    }

    public IshemiaEntity(AnalyseBloodFlowEntity analyseBloodFlow, double area, int zoneNumber, double x, double y) {
        this.analyseBloodFlow = analyseBloodFlow;
        this.area = area;
        this.zoneNumber = zoneNumber;
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
    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "zone_number", nullable = false)
    public int getZoneNumber() {
        return zoneNumber;
    }

    public void setZoneNumber(int zoneNumber) {
        this.zoneNumber = zoneNumber;
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
