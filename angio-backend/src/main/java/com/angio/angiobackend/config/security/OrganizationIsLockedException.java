package com.angio.angiobackend.config.security;

import org.springframework.security.authentication.AccountStatusException;

public class OrganizationIsLockedException extends AccountStatusException {

    public OrganizationIsLockedException(String msg) {
        super(msg);
    }

    public OrganizationIsLockedException(String msg, Throwable t) {
        super(msg, t);
    }
}
