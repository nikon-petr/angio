package com.angio.server.security.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tokens", schema = "public")
public class TokenEntity {
    private long id;
    private UserEntity user;
    private boolean enabled;
    private String os;
    private String browser;
    private String device;
    private Date expiration;
    private Date issuedAt;

    public TokenEntity() {
    }

    public TokenEntity(UserEntity user, boolean enabled, String os, String browser, String device) {
        this.user = user;
        this.enabled = enabled;
        this.os = os;
        this.browser = browser;
        this.device = device;
    }

    public TokenEntity(long id, UserEntity user, boolean enabled, String os, String browser, String device) {
        this.id = id;
        this.user = user;
        this.enabled = enabled;
        this.os = os;
        this.browser = browser;
        this.device = device;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "os", nullable = false)
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Column(name = "browser", nullable = false)
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    @Column(name = "device", nullable = false)
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Column(name = "expiration", nullable = true)
    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    @Column(name = "issued_at", nullable = true)
    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }
}
