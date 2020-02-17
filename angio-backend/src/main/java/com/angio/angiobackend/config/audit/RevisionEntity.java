package com.angio.angiobackend.config.audit;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Table(name = "revinfo")
@Entity
@org.hibernate.envers.RevisionEntity(RevisionListener.class)
public class RevisionEntity {

    @Id
    @GeneratedValue
    @RevisionNumber
    @Column(name = "rev")
    private int id;

    @RevisionTimestamp
    @Column(name = "revtstmp")
    private long timestamp;

    @Type(type="pg-uuid")
    @Column(name = "auditor_id")
    private UUID auditorId;
}
