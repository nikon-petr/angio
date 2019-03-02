package com.angio.angiobackend.api.user.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.OperationException;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.security.entity.Role;
import com.angio.angiobackend.api.security.repository.RoleRepository;
import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserLockedDto;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.mapper.UserMapper;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import com.angio.angiobackend.api.user.service.UserService;
import com.angio.angiobackend.util.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
     * Get user id from context.
     *
     * @return user uuid
     */
    @Override
    @PreAuthorize("isAuthenticated()")
    public UUID getUserIdFromContext() {
        return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Create new user accounts with property enabled=false.
     *
     * @param dtos user data to create
     * @return created user data
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public List<NewUserDto> createUsers(@NonNull List<NewUserDto> dtos) {

        log.trace("createUsers() - start");
        if (dtos.size() == 0) {
            log.trace("createUsers() - empty user list");
            return Collections.emptyList();
        }

        log.trace("createUsers() - checking all requested email is unique");
        checkEmailUnique(dtos);

        log.trace("createUsers() - fetch needed roles");
        List<Role> newUserRoles = findRolesForUsers(dtos);

        log.trace("createUsers() - checking that creator is owner of requested roles");
        checkAllowedRoles(newUserRoles);

        log.trace("createUsers() - generate passwords and save users");
        Map<String, User> passwordsAndNewUsers = dtos.stream()
                .map(dto -> new AbstractMap.SimpleEntry<>(PasswordUtils.generateNumberPassword(6), new User()
                        .setEmail(dto.getEmail())
                        .setEnabled(false)
                        .setLocked(false)
                        .setRoles(newUserRoles.stream()
                                .filter(role -> dto.getRoleIds().contains(role.getId()))
                                .collect(Collectors.toSet()))))
                .peek(entry -> entry.getValue().setPassword(passwordEncoder.encode(entry.getKey())))
                .peek(entry -> entry.setValue(userRepository.save(entry.getValue())))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        log.info("createUsers() - result: {}", passwordsAndNewUsers);

        log.trace("createUsers() - notify created users by email");
        // TODO: add call of email service for sending message with password

        log.trace("createUsers() - end");
        return userMapper.toNewUserDtos(new ArrayList<>(passwordsAndNewUsers.values()));
    }

    /**
     * Get user by id.
     *
     * @param id user id
     * @return user data
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('USER_VIEW') or #id == @userService.userIdFromContext")
    public UserDetailsDto getUserById(UUID id) {
        return userMapper.toDetailedDto(findUserEntityByUuid(id));
    }

    /**
     * Update user data with given id or throw {@link ResourceNotFoundException} if not found
     *
     * @param dto dto
     * @return updated user
     */
    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and #id == @userService.userIdFromContext")
    public UserDetailsDto updateUser(@NonNull UUID id, @NonNull UpdateUserDto dto) {

        log.trace("updateUser() - start, id: {}", id);
        User user = findUserEntityByUuid(id);

        log.trace("updateUser() - merge dto to entity");
        userMapper.updateEntity(dto, user);

        log.trace("updateUser() - save updated user and return");
        return userMapper.toDetailedDto(userRepository.save(user));
    }

    /**
     * Change user password.
     *
     * @param id user id
     * @param dto dto
     * @return updated user
     */
    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and #id == @userService.userIdFromContext")
    public UserDetailsDto changePassword(@NonNull UUID id, @NonNull ChangePasswordDto dto) {

        log.trace("changePassword() - start, id: {}", id);
        User user = findUserEntityByUuid(id);

        log.trace("changePassword() - check old password");
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password check failure");
        }

        log.trace("changePassword() - save new password");
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user = userRepository.save(user);

        log.trace("changePassword() - end");
        return userMapper.toDetailedDto(user);
    }

    /**
     * Change user locked property.
     *
     * @param id user id
     * @param dto dto containing new value
     * @return updated user
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public UserDetailsDto changeUserLocked(@NonNull UUID id, @NonNull UserLockedDto dto) {

        log.trace("changeUserLocked() - start, id: {}", id);
        User user = findUserEntityByUuid(id);

        log.trace("changePassword() - change and save locked");
        user.setLocked(dto.getLocked());
        user = userRepository.save(user);

        log.trace("changePassword() - end");
        return userMapper.toDetailedDto(user);
    }

    private void checkEmailUnique(List<NewUserDto> dtos) {
        List<User> existingUsers = userRepository.findByEmailIn(dtos.stream()
                .map(NewUserDto::getEmail)
                .collect(Collectors.toList()));
        if (existingUsers.size() > 0) {
            throw new OperationException(
                    msa.getMessage("errors.api.user.emailNotUnique", new Object[]{existingUsers.stream()
                            .map(User::getEmail)
                            .collect(Collectors.toList())}));
        }
    }

    private List<Role> findRolesForUsers(List<NewUserDto> dtos) {
        List<Long> rolesIds = dtos.stream()
                .flatMap(dto -> dto.getRoleIds().stream())
                .distinct()
                .collect(Collectors.toList());
        List<Role> roles = roleRepository.findAllById(rolesIds);

        if (rolesIds.size() != roles.size()) {
            rolesIds.removeAll(roles.stream().map(Role::getId).collect(Collectors.toList()));
            throw new OperationException(
                    msa.getMessage("errors.api.user.rolesForUserNotFound", new Object[]{rolesIds}));
        }

        return roles;
    }

    private void checkAllowedRoles(List<Role> roles) {
        User creator = getUserFromContext();

        if (!creator.getOwnedRolesToManage().containsAll(roles)) {
            List<Long> ownedRolesIds = creator.getOwnedRolesToManage().stream()
                    .map(Role::getId)
                    .collect(Collectors.toList());
            throw new OperationException(
                    msa.getMessage("errors.api.user.rolesNotOwnedUser", new Object[]{ownedRolesIds}));
        }
    }
}
