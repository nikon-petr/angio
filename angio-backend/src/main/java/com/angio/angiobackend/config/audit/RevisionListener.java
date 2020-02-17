package com.angio.angiobackend.config.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@Slf4j
public class RevisionListener implements org.hibernate.envers.RevisionListener {

    public void newRevision(final Object revisionInfo) {

        log.trace("newRevision() - start");
        RevisionEntity auditedRevisionEntity = (RevisionEntity) revisionInfo;
        UUID auditorUuid = null;

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            auditorUuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        }

        log.trace("newRevision() - inject auditor_id: {}", auditorUuid);
        auditedRevisionEntity.setAuditorId(auditorUuid);

        log.trace("newRevision() - end");
    }
}
