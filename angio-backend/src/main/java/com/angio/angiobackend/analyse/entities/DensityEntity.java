package com.angio.angiobackend.analyse.entities;

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
@Table(name = "densities", schema = "public")
public class DensityEntity {

    private long id;
    private AnalyseBloodFlowEntity analyseBloodFlow;
    private int sectorNumber;
    private double density;

    public DensityEntity() {
    }

    public DensityEntity(AnalyseBloodFlowEntity analyseBloodFlow, int sectorNumber, double density) {
        this.analyseBloodFlow = analyseBloodFlow;
        this.sectorNumber = sectorNumber;
        this.density = density;
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

    @Column(name = "sector_number", nullable = false)
    public int getSectorNumber() {
        return sectorNumber;
    }

    public void setSectorNumber(int sectorNumber) {
        this.sectorNumber = sectorNumber;
    }

    @Column(name = "density", nullable = false)
    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }
}
