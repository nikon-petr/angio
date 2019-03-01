package com.angio.angiobackend.api.user.entities;

import com.angio.angiobackend.api.analyse.entity.Analyse;
import com.angio.angiobackend.api.common.embeddable.FullName;
import com.angio.angiobackend.api.security.entity.Permission;
import com.angio.angiobackend.api.security.entity.Role;
import com.angio.angiobackend.api.security.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Accessors(chain = true)
@ToString(exclude = {"roles", "tokens", "analyses"})
@EqualsAndHashCode(exclude = {"id", "roles", "tokens", "analyses"})
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Embedded
    private FullName fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "locked", nullable = false)
    private boolean locked;

    @NotAudited
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @NotAudited
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "user",
            orphanRemoval = true
    )
    private Set<Token> tokens = new HashSet<>();

    @NotAudited
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "additionalInfo.diagnostician",
            orphanRemoval = true
    )
    private Set<Analyse> analyses = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getPermissions(roles);
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Set<Role> getRolesOwner() {
        Set<Role> ownedRoles = new HashSet<>();
        for (Role role : roles) {
            ownedRoles.addAll(role.getRolesOwner());
        }

        return ownedRoles;
    }

    private List<Permission> getPermissions(Collection<Role> roles) {
        List<Permission> permissions = new ArrayList<>();
        for (Role role : roles) {
            permissions.addAll(role.getPermissions());
        }
        return permissions;
    }
}