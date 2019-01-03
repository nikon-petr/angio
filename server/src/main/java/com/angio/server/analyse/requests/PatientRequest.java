package com.angio.server.analyse.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PatientRequest implements Serializable{

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
}