package com.angio.angiobackend.api.analyse.entity;

import com.angio.angiobackend.api.analyse.embeddable.AdditionalInfo;
import com.angio.angiobackend.api.analyse.embeddable.AnalyseStatus;
import com.angio.angiobackend.api.analyse.embeddable.BloodFlowAnalyse;
import com.angio.angiobackend.api.analyse.embeddable.GeometricAnalyse;
import com.angio.angiobackend.api.uploads.entity.StaticFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Slf4j
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"originalImage"})
@EqualsAndHashCode(exclude = {"id", "originalImage"})
@Audited
@Entity
@Table(name = "analyses")
public class Analyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "analyse_date", nullable = false)
    private Date analyseDate;

    @Embedded
    private AnalyseStatus status;

    @Embedded
    private AdditionalInfo additionalInfo;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_image_id")
    private StaticFile originalImage;

    @Access(AccessType.PROPERTY)
    @Embedded
    private GeometricAnalyse geometricAnalyse;

    @Access(AccessType.PROPERTY)
    @Embedded
    private BloodFlowAnalyse bloodFlowAnalyse;

    public AdditionalInfo getAdditionalInfo() {
        if (additionalInfo == null) {
            setAdditionalInfo(new AdditionalInfo());
        }
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public GeometricAnalyse getGeometricAnalyse()  {
        if (geometricAnalyse == null) {
            setGeometricAnalyse(new GeometricAnalyse());
        }
        return geometricAnalyse;
    }

    public void setGeometricAnalyse(GeometricAnalyse geometricAnalyse) {
        this.geometricAnalyse = geometricAnalyse;
        this.geometricAnalyse.setAnalyse(this);
    }

    public BloodFlowAnalyse getBloodFlowAnalyse() {
        if (bloodFlowAnalyse == null) {
            setBloodFlowAnalyse(new BloodFlowAnalyse());
        }
        return bloodFlowAnalyse;
    }

    public void setBloodFlowAnalyse(BloodFlowAnalyse bloodFlowAnalyse) {
        this.bloodFlowAnalyse = bloodFlowAnalyse;
        this.bloodFlowAnalyse.setAnalyse(this);
    }
}