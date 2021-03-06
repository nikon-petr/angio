package com.angio.angiobackend.api.analyse.embeddable;

import com.angio.angiobackend.api.analyse.type.AnalyseType;
import com.angio.angiobackend.api.patient.entity.Patient;
import com.angio.angiobackend.api.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"diagnostician", "patient"})
@EqualsAndHashCode(exclude = {"diagnostician", "patient"})
@Embeddable
public class AdditionalInfo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User diagnostician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "name", nullable = false, length = 75)
    private String name;

    @Column(name = "short_description", nullable = false, length = 100)
    private String shortDescription;

    @Column(name = "full_description", length = 1000)
    private String fullDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "analyse_type", nullable = false, length = 20)
    private AnalyseType type;

    @Column(name = "comment", nullable = false, length = 125)
    private String comment;

    @Column(name = "conclusion", length = 1000)
    private String conclusion;
}