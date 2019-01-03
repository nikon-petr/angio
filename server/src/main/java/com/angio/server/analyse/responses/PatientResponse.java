package com.angio.server.analyse.responses;

import com.angio.server.analyse.entities.PatientEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class PatientResponse implements Serializable {
    private long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String email;
    private String phone;
    private String policy;
    private String bday;
    private String address;
    private String work;
    private String comment;

    public PatientResponse(){

    }

    public PatientResponse(long id, String firstname, String lastname, String patronymic,
                          String email, String phone, String policy, String bday, String address, String work, String comment) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.policy = policy;
        this.bday = bday;
        this.address = address;
        this.work = work;
        this.comment = comment;
    }

    public PatientResponse(PatientEntity patientEntity){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(patientEntity.getBday());

        this.id = patientEntity.getId();
        this.firstname = patientEntity.getFirstname();
        this.lastname = patientEntity.getLastname();
        this.patronymic = patientEntity.getPatronymic();
        this.email = patientEntity.getEmail();
        this.phone = patientEntity.getPhone();
        this.policy = patientEntity.getPolicy();
        this.bday = date;
        this.address = patientEntity.getLocationAddress();
        this.work = patientEntity.getWorkAddress();
        this.comment = patientEntity.getComment();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comments) {
        this.comment = comments;
    }
}