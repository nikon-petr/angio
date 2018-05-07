package com.angio.server.analyse.entities;

import com.angio.server.analyse.requests.PatientRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients", catalog = "public")
public class PatientEntity {
    private long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String email;
    private String phone;
    private Date bday;
    private String location_address;
    private String work_address;
    private String comment;
    private String policy;
    private Set<AnalyseInfoEntity> analysesInfo = new HashSet<>(0);

    public PatientEntity(){

    }

    public PatientEntity(String firstname, String lastname, String patronymic, String email, String phone, Date bday,
                         String location_address, String work_address, String comment, String policy) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.bday = bday;
        this.location_address = location_address;
        this.work_address = work_address;
        this.comment = comment;
        this.policy = policy;
    }

    public PatientEntity(PatientRequest patientRequest){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
            Date bDay = sdf.parse(patientRequest.getBday());
            this.firstname = patientRequest.getFirstname();
            this.lastname = patientRequest.getLastname();
            this.patronymic = patientRequest.getPatronymic();
            this.email = patientRequest.getEmail();
            this.phone = patientRequest.getPhone();
            this.bday = new Timestamp(bDay.getTime());
            this.phone = patientRequest.getPhone();
            this.work_address = patientRequest.getWork();
            this.location_address = patientRequest.getAddress();
            this.comment = patientRequest.getComments();
            this.policy = patientRequest.getPolicy();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    @Column(name = "firstname", nullable = false, length = 30)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", nullable = false, length = 30)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "patronymic", length = 30)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Column(name = "email", nullable = false, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone", nullable = false, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "bday", nullable = false)
    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    @Column(name = "location_address", nullable = false, length = 100)
    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    @Column(name = "work_address", nullable = false, length = 100)
    public String getWork_address() {
        return work_address;
    }

    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }

    @Column(name = "comment", length = 1000)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    public Set<AnalyseInfoEntity> getAnalysesInfo() {
        return analysesInfo;
    }

    public void setAnalysesInfo(Set<AnalyseInfoEntity> analysesInfo) {
        this.analysesInfo = analysesInfo;
    }

    @Column(name = "policy", length = 16)
    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}