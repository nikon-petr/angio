package com.angio.angiobackend.api.analyse.entity;

import com.angio.angiobackend.api.uploads.entity.StaticFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"vesselImage", "mainVesselImage", "analyse"})
@EqualsAndHashCode(exclude = {"id", "vesselImage", "mainVesselImage", "analyse"})
@Cacheable
@Entity
@Table(name = "vessels")
public class Vessel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_image_id")
    private StaticFile vesselImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_vessel_image_id")
    private StaticFile mainVesselImage;

    @Column(name = "tortuosity_degree", nullable = false)
    private Float tortuosityDegree;

    @Column(name = "count_of_branches", nullable = false)
    private Integer countOfBranches;

    @Column(name = "branching_degree", nullable = false)
    private Float branchingDegree;

    @Column(name = "area", nullable = false)
    private Float area;

    @Column(name = "area_percent", nullable = false)
    private Float areaPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analyse_id")
    private Analyse analyse;
}