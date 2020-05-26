package com.angio.angiobackend.api.user.service;

import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.init.Permissions;
import org.springframework.security.core.GrantedAuthority;
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

    /**
     * Check current user has permission
     *
     * @param permission permission name
     * @return checking result
     */
    static boolean hasPermission(Permissions permission) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(p -> p.equals(permission.name()));
    }
}
