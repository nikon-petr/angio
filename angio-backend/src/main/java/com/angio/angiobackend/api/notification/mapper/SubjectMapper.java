package com.angio.angiobackend.api.notification.mapper;

import com.angio.angiobackend.api.notification.dto.AbstractSubject;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper {

    default String toPlainSubject(AbstractSubject subject) {
        return subject.getName();
    }

    default AbstractSubject toAbstractSubject(String subject) {
        return () -> subject;
    }
}
