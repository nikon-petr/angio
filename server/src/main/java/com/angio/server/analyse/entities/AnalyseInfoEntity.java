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
    private String short_description;
    private String full_description;
    private String analyse_type;
    private String comment;
    private String img;
    private Date analyse_date;
    private boolean finished;
    private String conclusion;
    private Set<AnalyseGeometricEntity> analyseGeometric = new HashSet<>(0);

    public AnalyseInfoEntity(){

    }

    public AnalyseInfoEntity(UserEntity user, PatientEntity patient, String name, String short_description,
                             String full_description, String analyse_type, String comment, String img,
                             Date analyse_date, String conclusion, boolean finished) {
        this.user = user;
        this.patient = patient;
        this.name = name;
        this.short_description = short_description;
        this.full_description = full_description;
        this.analyse_type = analyse_type;
        this.comment = comment;
        this.img = img;
        this.analyse_date = analyse_date;
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
    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    @Column(name = "full_description", length = 1000)
    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    @Column(name = "analyse_type", nullable = false, length = 200)
    public String getAnalyse_type() {
        return analyse_type;
    }

    public void setAnalyse_type(String analyse_type) {
        this.analyse_type = analyse_type;
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
    public Date getAnalyse_date() {
        return analyse_date;
    }

    public void setAnalyse_date(Date analyse_date) {
        this.analyse_date = analyse_date;
    }

    @Column(name = "conclusion", length = 1000)
    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "analyseInfo")
    public Set<AnalyseGeometricEntity> getAnalyseGeometric() {
        return analyseGeometric;
    }

    public void setAnalyseGeometric(Set<AnalyseGeometricEntity> analyseGeometric) {
        this.analyseGeometric = analyseGeometric;
    }

    @Column(name = "finished", nullable = false)
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}