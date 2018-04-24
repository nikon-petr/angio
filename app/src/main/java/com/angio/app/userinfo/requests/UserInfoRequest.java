package com.angio.app.userinfo.requests;

import java.io.Serializable;

public class UserInfoRequest implements Serializable{
    private String username;
    private String firstname;
    private String lastname;

    public UserInfoRequest(){
        super();
    }

    public UserInfoRequest(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}