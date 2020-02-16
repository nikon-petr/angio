package com.angio.angiobackend.api.user.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import com.angio.angiobackend.api.user.service.CurrentUserResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class CurrentUserResolverImpl implements CurrentUserResolver {

    private final UserRepository userRepository;
    private final DynamicLocaleMessageSourceAccessor msa;

    /**
     * Get user from security context and find it in database.
     *
     * @return user entity
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public User getCurrentUser() {
        log.debug("getCurrentUser() - start");

        UUID uuid = CurrentUserResolver.getCurrentUserUuid();

        log.debug("getCurrentUser() - user uuid: {}", uuid);
        return userRepository.findById(uuid)
                .orElseThrow(() -> new UnauthorizedUserException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {uuid})));

    }
}
