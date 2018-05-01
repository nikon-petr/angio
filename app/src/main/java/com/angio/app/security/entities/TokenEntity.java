package com.angio.app.security.entities;

import javax.persistence.*;

@Entity
@Table(name = "tokens", catalog = "public")
public class TokenEntity {
    private long id;
    private UserEntity user;
    private String token;
    private boolean enabled;

    public TokenEntity() {
    }

    public TokenEntity(UserEntity user, String token, boolean enabled) {
        this.user = user;
        this.token = token;
        this.enabled = enabled;
    }

    public TokenEntity(long id, UserEntity user, String token, boolean enabled) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.enabled = enabled;
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
    public UserEntity getUsername() {
        return user;
    }

    public void setUsername(UserEntity user) {
        this.user = user;
    }

    @Column(name = "token", nullable = false, length = 1000)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
