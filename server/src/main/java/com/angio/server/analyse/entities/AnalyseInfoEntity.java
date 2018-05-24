package com.angio.server.analyse.entities;

import com.angio.server.security.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "analyses_info", catalog = "public")
public class AnalyseInfoEntity {
    private long id;
    private UserEntity user;
    private PatientEntity patient;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String analyseType;
    private String comment;
    private String img;
    private Date analyseDate;
    private boolean finished;
    private String conclusion;
    private AnalyseGeometricEntity analyseGeometric;
    private AnalyseBloodFlowEntity analyseBloodFlow;

    public AnalyseInfoEntity(){

    }

    public AnalyseInfoEntity(UserEntity user, PatientEntity patient, String name, String shortDescription,
                             String fullDescription, String analyseType, String comment, String img,
                             Date analyseDate, String conclusion, boolean finished) {
        this.user = user;
        this.patient = patient;
        this.name = name;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.analyseType = analyseType;
        this.comment = comment;
        this.img = img;
        this.analyseDate = analyseDate;
        this.conclusion = conclusion;
        this.finished = finished;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "short_description", nullable = false, length = 500)
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String short_description) {
        this.shortDescription = short_description;
    }

    @Column(name = "full_description", length = 1000)
    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    @Column(name = "analyse_type", nullable = false, length = 200)
    public String getAnalyseType() {
        return analyseType;
    }

    public void setAnalyseType(String analyseType) {
        this.analyseType = analyseType;
    }

    @Column(name = "comment", nullable = false, length = 1000)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "img", nullable = false, length = 400)
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "analyse_date", nullable = false)
    public Date getAnalyseDate() {
        return analyseDate;
    }

    public void setAnalyseDate(Date analyse_date) {
        this.analyseDate = analyse_date;
    }

    @Column(name = "conclusion", length = 1000)
    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "analyseInfo")
    public AnalyseGeometricEntity getAnalyseGeometric() {
        return analyseGeometric;
    }

    public void setAnalyseGeometric(AnalyseGeometricEntity analyseGeometric) {
        this.analyseGeometric = analyseGeometric;
    }

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "analyseInfo")
    public AnalyseBloodFlowEntity getAnalyseBloodFlow() {
        return analyseBloodFlow;
    }

    public void setAnalyseBloodFlow(AnalyseBloodFlowEntity analyseBloodFlow) {
        this.analyseBloodFlow = analyseBloodFlow;
    }

    @Column(name = "finished", nullable = false)
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}