package com.angio.server.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities", catalog = "public",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"username", "authority"}
        ))
public class AuthorityEntity implements GrantedAuthority, Serializable {
    private long id;
    private UserEntity user;
    private String authority;

    public AuthorityEntity() {
    }

    public AuthorityEntity(UserEntity user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "username", nullable = false)
    public UserEntity getUsername() {
        return user;
    }

    public void setUsername(UserEntity user) {
        this.user = user;
    }

    @Override
    @Column(name = "authority", nullable = false, length = 30)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof AuthorityEntity)) {
            return false;
        }

        if (!(getUsername().equals(((AuthorityEntity) obj).getUsername()))) {
            return false;
        }

        if (!(getAuthority().equals(((AuthorityEntity) obj).getAuthority()))) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime + (getAuthority().hashCode() + getUsername().getUsername().hashCode());
    }
}
