package com.angio.angiobackend.api.analyse.entity;

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
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"analyse"})
@EqualsAndHashCode(exclude = {"id", "analyse"})
@Cacheable
@Entity
@Table(name = "densities", schema = "public")
public class DensityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sector_number", nullable = false)
    private Integer sectorNumber;

    @Column(name = "density", nullable = false)
    private Double density;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analyse_id")
    private AnalyseEntity analyse;
}
