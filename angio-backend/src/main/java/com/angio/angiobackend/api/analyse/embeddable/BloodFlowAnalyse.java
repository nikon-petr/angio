package com.angio.angiobackend.api.analyse.embeddable;

import com.angio.angiobackend.api.analyse.entity.Analyse;
import com.angio.angiobackend.api.analyse.entity.Density;
import com.angio.angiobackend.api.analyse.entity.Ischemia;
import com.angio.angiobackend.api.uploads.entity.StaticFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

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

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ischemia_image_id")
    private StaticFile ischemiaImage;

    @NotAudited
    @OrderBy("zone_number ASC")
    @OneToMany(
            mappedBy = "analyse",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Ischemia> ischemias = new HashSet<>();

    @Embedded
    private Macula macula;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "density_image_id")
    private StaticFile densityImage;

    @NotAudited
    @OrderBy("sector_number ASC")
    @OneToMany(
            mappedBy = "analyse",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Density> densities = new HashSet<>();

    @Transient
    private Analyse analyse;

    public void addIschemia(Ischemia ischemia) {
        ischemias.add(ischemia);
        ischemia.setAnalyse(analyse);
    }

    public void addDensity(Density density) {
        densities.add(density);
        density.setAnalyse(analyse);
    }
}
