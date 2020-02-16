package com.angio.angiobackend.api.user.service;

import com.angio.angiobackend.api.user.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public interface CurrentUserResolver {


    User getCurrentUser();

    /**
     * Get user id from security context.
     *
     * @return user uuid
     */
    static UUID getCurrentUserUuid() {
        return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
