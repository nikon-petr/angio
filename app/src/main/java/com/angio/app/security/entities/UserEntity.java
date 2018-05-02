package com.angio.app.security.entities;

import com.angio.app.analyse.analyse_info.entities.AnalyseInfoEntity;
import com.angio.app.userinfo.entities.UserInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", catalog = "public")
public class UserEntity implements UserDetails, Serializable {

    private String username;
    private String password;
    private boolean enabled;
    private Set<AuthorityEntity> authorities = new HashSet<>(0);
    private Date lastPasswordResetDate;
    private Set<AnalyseInfoEntity> analysesInfo = new HashSet<>(0);
    private UserInfoEntity userInfo;

    public UserEntity() {

    }

    public UserEntity(String username, String password, boolean enabled, Date lastPasswordResetDate) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Id
    @Override
    @Column(name = "username", unique = true, nullable = false, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @Column(name = "password", nullable = false, length = 120)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "username")
    public Set<AuthorityEntity> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    @Column(name = "last_password_reset_date", nullable = false)
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<AnalyseInfoEntity> getAnalysesInfo() {
        return analysesInfo;
    }

    public void setAnalysesInfo(Set<AnalyseInfoEntity> analysesInfo) {
        this.analysesInfo = analysesInfo;
    }

    @JsonIgnore
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }
}