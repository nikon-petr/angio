package com.angio.angiobackend.api.analyse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@Entity
@Table(name = "ischemias", schema = "public")
public class IshemiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "area", nullable = false)
    private Double area;

    @Column(name = "zone_number", nullable = false)
    private Integer zoneNumber;

    @Column(name = "x", nullable = false)
    private Double x;

    @Column(name = "y", nullable = false)
    private Double y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analyse_id")
    private AnalyseEntity analyse;
}
