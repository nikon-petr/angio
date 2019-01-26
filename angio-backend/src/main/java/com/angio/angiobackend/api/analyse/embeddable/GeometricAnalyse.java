package com.angio.angiobackend.api.analyse.embeddable;

import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.analyse.entity.VesselEntity;
import com.angio.angiobackend.api.uploads.entity.StaticFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"vessels", "analyse"})
@EqualsAndHashCode(exclude = {"vessels", "analyse"})
@Embeddable
@Access(AccessType.FIELD)
public class GeometricAnalyse {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "binarized_image_id")
    private StaticFileEntity binarizedImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skeletonized_image_id")
    private StaticFileEntity skeletonizedImage;

    @OneToMany(
            mappedBy = "analyse",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<VesselEntity> vessels = new HashSet<>();

    @Transient
    private AnalyseEntity analyse;

    public void addVessel(VesselEntity vessel) {
        vessels.add(vessel);
        vessel.setAnalyse(analyse);
    }
}