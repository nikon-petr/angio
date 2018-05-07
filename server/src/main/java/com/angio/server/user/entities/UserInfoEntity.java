package com.angio.server.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.angio.server.security.entities.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users_info", catalog = "public")
public class UserInfoEntity {
    private long info_id;
    private UserEntity user;
    private String firstname;
    private String lastname;
    private Date modified_date;

    public UserInfoEntity(){

    }

    public UserInfoEntity(UserEntity user, String firstname, String lastname, Date modified_date) {
        this.user = user;
        this.firstname = firstname;
        this.lastname = lastname;
        this.modified_date = modified_date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id", nullable = false)
    public long getInfo_id() {
        return info_id;
    }

    public void setInfo_id(long info_id) {
        this.info_id = info_id;
    }

    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    @Column(name = "modified_date", nullable = false)
    public Date getModified_date() {
        return modified_date;
    }

    public void setModified_date(Date modified_date) {
        this.modified_date = modified_date;
    }
}