package com.angio.server.analyse.entities;

import com.angio.server.security.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
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

@Data
@NoArgsConstructor
@ToString(exclude = {"user", "patient", "analyseGeometric", "analyseBloodFlow"})
@Entity
@Table(name = "analyses_info", schema = "public")
public class AnalyseInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UserEntity user;

    @JsonIgnore
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
    private String analyseType;

    @Column(name = "comment", nullable = false, length = 1000)
    private String comment;

    @Column(name = "img", nullable = false, length = 400)
    private String img;

    @Column(name = "analyse_date", nullable = false)
    private Date analyseDate;

    @Column(name = "finished", nullable = false)
    private boolean finished;

    @Column(name = "conclusion", length = 1000)
    private String conclusion;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "analyseInfo")
    private AnalyseGeometricEntity analyseGeometric;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "analyseInfo")
    private AnalyseBloodFlowEntity analyseBloodFlow;
}