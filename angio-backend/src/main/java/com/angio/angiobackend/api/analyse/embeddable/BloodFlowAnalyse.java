package com.angio.angiobackend.api.analyse.embeddable;

import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.analyse.entity.DensityEntity;
import com.angio.angiobackend.api.analyse.entity.IshemiaEntity;
import com.angio.angiobackend.api.uploads.entity.StaticFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"ischemiaImage", "ischemias", "densityImage", "densities", "analyse"})
@EqualsAndHashCode(exclude = {"ischemiaImage", "ischemias", "densityImage", "densities", "analyse"})
@Embeddable
@Access(AccessType.FIELD)
public class BloodFlowAnalyse {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ischemia_image_id")
    private StaticFileEntity ischemiaImage;

    @OrderBy("zone_number ASC")
    @OneToMany(
            mappedBy = "analyse",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<IshemiaEntity> ischemias = new HashSet<>();

    @Embedded
    private Macula macula;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "density_image_id")
    private StaticFileEntity densityImage;

    @OrderBy("sector_number ASC")
    @OneToMany(
            mappedBy = "analyse",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DensityEntity> densities = new HashSet<>();

    @Transient
    private AnalyseEntity analyse;

    public void addIschemia(IshemiaEntity ischemia) {
        ischemias.add(ischemia);
        ischemia.setAnalyse(analyse);
    }

    public void addDensity(DensityEntity density) {
        densities.add(density);
        density.setAnalyse(analyse);
    }
}
