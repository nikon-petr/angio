package com.angio.angiobackend.api.user.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Accessors(chain = true)
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Cacheable
@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue
    @Type(type="pg-uuid")
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "dark_theme_enabled")
    private Boolean darkThemeEnabled;

    @Column(name = "locale")
    private String locale;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;
}
