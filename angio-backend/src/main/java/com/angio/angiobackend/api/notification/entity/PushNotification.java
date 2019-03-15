package com.angio.angiobackend.api.notification.entity;

import com.angio.angiobackend.api.notification.type.NotificationType;
import com.angio.angiobackend.api.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Data
@Accessors(chain = true)
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "push_notifications")
public class PushNotification {

    @Id
    @GeneratedValue
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = "date")
    private Date date;

    @Column(name = "type")
    private NotificationType type;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "payload")
    private String payload;

    @Column(name = "read")
    private Boolean read;

    @Column(name = "subject")
    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
