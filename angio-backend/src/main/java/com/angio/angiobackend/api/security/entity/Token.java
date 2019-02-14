package com.angio.angiobackend.api.security.entity;

import com.angio.angiobackend.api.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"id", "user"})
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "graph.Order.user",
        attributeNodes = {
                @NamedAttributeNode("user")
        }
)
@Entity
@Table(name = "tokens")
public class Token implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "issued_at")
    private Date issuedAt;

    @Column(name = "expires_in")
    private Date expiresIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
