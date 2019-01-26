package com.angio.angiobackend.api.user.entities;

import com.angio.angiobackend.api.common.embeddable.FullName;
import com.angio.angiobackend.api.security.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users_info", schema = "public")
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id", nullable = false)
    private long infoId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UserEntity user;

    @Embedded
    private FullName fullName;

    @Column(name = "modified_date", nullable = false)
    private Date modifiedDate;

    public UserInfoEntity(UserEntity user, FullName fullName, Date modifiedDate) {
        this.user = user;
        this.fullName = fullName;
        this.modifiedDate = modifiedDate;
    }
}