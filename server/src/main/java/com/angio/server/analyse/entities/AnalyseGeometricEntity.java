package com.angio.server.analyse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "analyses_geometric", catalog = "public")
public class AnalyseGeometricEntity {
    private long id;
    private AnalyseInfoEntity analyseInfo;
    private String binarizedImage;
    private String skelImage;
    private Set<VesselEntity> vessels = new HashSet<>(0);

    public AnalyseGeometricEntity() {

    }

    public AnalyseGeometricEntity(AnalyseInfoEntity analyseInfo, String binarizedImage, String skelImage) {
        this.analyseInfo = analyseInfo;
        this.binarizedImage = binarizedImage;
        this.skelImage = skelImage;
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
    @JoinColumn(name = "analyse_info_id", nullable = false)
    public AnalyseInfoEntity getAnalyseInfo() {
        return analyseInfo;
    }

    public void setAnalyseInfo(AnalyseInfoEntity analyseInfo) {
        this.analyseInfo = analyseInfo;
    }

    @Column(name = "binarized_image", nullable = false, length = 400)
    public String getBinarizedImage() {
        return binarizedImage;
    }

    public void setBinarizedImage(String binarizedImage) {
        this.binarizedImage = binarizedImage;
    }

    @Column(name = "skel_image", nullable = false, length = 400)
    public String getSkelImage() {
        return skelImage;
    }

    public void setSkelImage(String skel_image) {
        this.skelImage = skel_image;
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