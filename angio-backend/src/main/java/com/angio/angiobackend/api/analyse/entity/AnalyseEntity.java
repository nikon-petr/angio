package com.angio.angiobackend.api.analyse.entity;

import com.angio.angiobackend.api.analyse.embeddable.AnalyseStatus;
import com.angio.angiobackend.api.analyse.embeddable.BloodFlowAnalyse;
import com.angio.angiobackend.api.analyse.embeddable.GeometricAnalyse;
import com.angio.angiobackend.api.analyse.type.AnalyseType;
import com.angio.angiobackend.api.patient.entity.PatientEntity;
import com.angio.angiobackend.api.security.entities.UserEntity;
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
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Slf4j
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user", "patient", "originalImage"})
@EqualsAndHashCode(exclude = {"id", "user", "patient", "originalImage"})
@Entity
@Table(name = "analyses")
public class AnalyseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "short_description", nullable = false, length = 500)
    private String shortDescription;

    @Column(name = "full_description", length = 1000)
    private String fullDescription;

    @Column(name = "analyse_type", nullable = false, length = 200)
    private AnalyseType type;

    @Column(name = "comment", nullable = false, length = 1000)
    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_image_id")
    private StaticFileEntity originalImage;

    @Column(name = "analyse_date", nullable = false)
    private Date analyseDate;

    @Embedded
    private AnalyseStatus status;

    @Column(name = "conclusion", length = 1000)
    private String conclusion;

    @Access(AccessType.PROPERTY)
    @Embedded
    private GeometricAnalyse geometricAnalyse;

    @Access(AccessType.PROPERTY)
    @Embedded
    private BloodFlowAnalyse bloodFlowAnalyse;

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