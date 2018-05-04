package com.angio.app.analyse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "analyses_geometric", catalog = "public")
public class AnalyseGeometricEntity {
    private long id;
    private AnalyseInfoEntity analyseInfo;
    private String original_image;
    private String binarized_image;
    private String skel_image;
    private Set<VesselEntity> vessels = new HashSet<>(0);

    public AnalyseGeometricEntity() {

    }

    public AnalyseGeometricEntity(AnalyseInfoEntity analyseInfo, String original_image, String binarized_image, String skel_image) {
        this.analyseInfo = analyseInfo;
        this.original_image = original_image;
        this.binarized_image = binarized_image;
        this.skel_image = skel_image;
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
    @JoinColumn(name = "analyse_info_id", nullable = false)
    public AnalyseInfoEntity getAnalyseInfo() {
        return analyseInfo;
    }

    public void setAnalyseInfo(AnalyseInfoEntity analyseInfo) {
        this.analyseInfo = analyseInfo;
    }

    @Column(name = "original_image", nullable = false, length = 400)
    public String getOriginal_image() {
        return original_image;
    }

    public void setOriginal_image(String original_image) {
        this.original_image = original_image;
    }

    @Column(name = "binarized_image", nullable = false, length = 400)
    public String getBinarized_image() {
        return binarized_image;
    }

    public void setBinarized_image(String binarized_image) {
        this.binarized_image = binarized_image;
    }

    @Column(name = "skel_image", nullable = false, length = 400)
    public String getSkel_image() {
        return skel_image;
    }

    public void setSkel_image(String skel_image) {
        this.skel_image = skel_image;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "analyseGeometric")
    public Set<VesselEntity> getVessels() {
        return vessels;
    }

    public void setVessels(Set<VesselEntity> vessels) {
        this.vessels = vessels;
    }
}