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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"analyse"})
@EqualsAndHashCode(exclude = {"id", "analyse"})
@Cacheable
@Entity
@Table(name = "retinal_positive_extremums", schema = "public")
public class RetinalPositiveExtremum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id")
    private StaticFile profileImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "angiogram_image_id")
    private StaticFile angiogramImage;

    @Column(name = "extremum_value", nullable = false)
    private Double extremumValue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analyse_id")
    private Analyse analyse;
}
