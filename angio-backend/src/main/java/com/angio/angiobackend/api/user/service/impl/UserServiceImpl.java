package com.angio.angiobackend.api.user.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import com.angio.angiobackend.api.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DynamicLocaleMessageSourceAccessor msa;

    /**
     * Find user by id and return entity object.
     *
     * @param uuid user id
     * @return user entity
     */
    @Override
    @Transactional(readOnly = true)
    public User findUserEntityByUuid(@NonNull String uuid) {
        log.debug("findUserEntityByUuid() - start id: {}", uuid);
        return userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UsernameNotFoundException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {uuid})));
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserEntityByEmail(@NonNull String email) {
        log.debug("findUserEntityByEmail() - start id: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        msa.getMessage("errors.api.user.userWithEmailNotFound", new Object[] {email})));
    }

    /**
     * Get user from security context and find it in database.
     *
     * @return user entity
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public User getUserFromContext() {
        String uuid = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("getUserFromContext() - start id: {}", uuid);
        return userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UnauthorizedUserException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {uuid})));
    }
}
