package com.angio.app.userinfo.requests;

import java.io.Serializable;

public class UserInfoUpdateRequest implements Serializable{
    private long userinfo_id;
    private String username;
    private String firstname;
    private String lastname;

    public UserInfoUpdateRequest(){
            super();
        }

    public UserInfoUpdateRequest(String username, String firstname, String lastname) {
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

    public long getUserinfo_id() {
        return userinfo_id;
    }

    public void setUserinfo_id(long userinfo_id) {
        this.userinfo_id = userinfo_id;
    }
}