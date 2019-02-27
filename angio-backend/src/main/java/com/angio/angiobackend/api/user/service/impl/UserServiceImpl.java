package com.angio.angiobackend.api.user.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.user.dto.UserBaseDto;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final DynamicLocaleMessageSourceAccessor msa;

    /**
     * Find user by id and return entity object.
     *
     * @param id user uuid
     * @return user entity
     */
    @Override
    @Transactional(readOnly = true)
    public User findUserEntityByUuid(@NonNull UUID id) {
        log.debug("findUserEntityByUuid() - start id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {id})));
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

    /**
     * Update user data with given id or throw {@link ResourceNotFoundException} if not found
     *
     * @param dto dto
     * @return updating result
     */
    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public UserBaseDto updateUser(@NonNull UUID id, @NonNull UserBaseDto dto) {

        log.trace("updateUser() - start, id: {}", id);
        User user = findUserEntityByUuid(id);

        log.trace("updateUser() - merge dto to entity");
        userMapper.updateEntity(dto, user);

        log.trace("updateUser() - save updated user and return");
        return userMapper.toBaseDto(userRepository.save(user));
    }
}
