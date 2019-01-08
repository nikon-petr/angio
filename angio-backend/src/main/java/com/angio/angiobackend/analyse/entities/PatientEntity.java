package com.angio.angiobackend.analyse.entities;

import com.angio.angiobackend.common.embeddable.FullName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString(exclude = {"analysesInfo"})
@Entity
@Table(name = "patients", schema = "public")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Embedded
    private FullName fullName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 11)
    private String phone;

    @Column(name = "bday", nullable = false)
    private Date bday;

    @Column(name = "location_address", nullable = false, length = 100)
    private String locationAddress;

    @Column(name = "work_address", nullable = false, length = 100)
    private String workAddress;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "policy", length = 16)
    private String policy;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private Set<AnalyseInfoEntity> analysesInfo = new HashSet<>(0);
}