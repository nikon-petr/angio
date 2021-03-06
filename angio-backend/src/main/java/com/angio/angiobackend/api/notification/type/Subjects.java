package com.angio.angiobackend.api.notification.type;

import com.angio.angiobackend.api.notification.dto.AbstractSubject;

public enum Subjects implements AbstractSubject {

    COMMON,
    USER,
    ANALYSE;

    @Override
    public String getName() {
        return this.name();
    }
}
