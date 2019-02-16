package com.angio.angiobackend.api.patient.entity;

import com.angio.angiobackend.api.analyse.entity.Analyse;
import com.angio.angiobackend.api.common.embeddable.FullName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
import java.util.Set;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"analysesInfo"})
@EqualsAndHashCode(exclude = {"id", "analysesInfo"})
@Entity
@Table(name = "patients", schema = "public")
public class Patient {

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "additionalInfo.patient")
    private Set<Analyse> analysesInfo;
}