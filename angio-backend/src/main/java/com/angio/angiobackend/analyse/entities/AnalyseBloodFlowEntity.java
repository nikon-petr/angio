package com.angio.angiobackend.analyse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "analyses_bloodflow", schema = "public")
public class AnalyseBloodFlowEntity {

    private long id;
    private AnalyseInfoEntity analyseInfo;
    private String ishemiaImageFileName;
    private Set<IshemiaEntity> ischemias;
    private MakulaEntity makula;
    private String densityImageFileName;
    private Set<DensityEntity> densities;

    public AnalyseBloodFlowEntity() {
    }

    public AnalyseBloodFlowEntity(
            AnalyseInfoEntity analyseInfo,
            String ishemiaImageFileName,
            String densityImageFileName) {
        this.analyseInfo = analyseInfo;
        this.ishemiaImageFileName = ishemiaImageFileName;
        this.densityImageFileName = densityImageFileName;
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

    @Column(name = "ishemia_image", nullable = false)
    public String getIshemiaImageFileName() {
        return ishemiaImageFileName;
    }

    public void setIshemiaImageFileName(String ishemiaImageFileName) {
        this.ishemiaImageFileName = ishemiaImageFileName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "analyseBloodFlow")
    @OrderBy("zone_number ASC")
    public Set<IshemiaEntity> getIschemias() {
        return ischemias;
    }

    public void setIschemias(Set<IshemiaEntity> ishemiaAreas) {
        this.ischemias = ishemiaAreas;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "analyseBloodFlow")
    public MakulaEntity getMakula() {
        return makula;
    }

    public void setMakula(MakulaEntity makula) {
        this.makula = makula;
    }

    @Column(name = "density_image", nullable = false)
    public String getDensityImageFileName() {
        return densityImageFileName;
    }

    public void setDensityImageFileName(String densityImageFileName) {
        this.densityImageFileName = densityImageFileName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "analyseBloodFlow")
    @OrderBy("sector_number ASC")
    public Set<DensityEntity> getDensities() {
        return densities;
    }

    public void setDensities(Set<DensityEntity> densities) {
        this.densities = densities;
    }
}
