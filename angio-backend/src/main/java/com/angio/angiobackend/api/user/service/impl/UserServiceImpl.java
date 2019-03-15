package com.angio.angiobackend.api.user.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.dto.AbstractEmailDto;
import com.angio.angiobackend.api.common.embeddable.FullName;
import com.angio.angiobackend.api.common.exception.OperationException;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.notification.dto.NewNotificationDto;
import com.angio.angiobackend.api.notification.dto.SubjectDto;
import com.angio.angiobackend.api.notification.service.NotificationService;
import com.angio.angiobackend.api.notification.type.NotificationType;
import com.angio.angiobackend.api.security.entity.Role;
import com.angio.angiobackend.api.security.repository.RoleRepository;
import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.EnableUserDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.ResetUserDto;
import com.angio.angiobackend.api.user.dto.SettingsDto;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserLockedDto;
import com.angio.angiobackend.api.user.dto.email.RegistrationEmailDto;
import com.angio.angiobackend.api.user.dto.email.ResetPasswordDto;
import com.angio.angiobackend.api.user.dto.push.GreetingPushDto;
import com.angio.angiobackend.api.user.entities.Settings;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.mapper.SettingsMapper;
import com.angio.angiobackend.api.user.mapper.UserMapper;
import com.angio.angiobackend.api.user.repositories.SettingsRepository;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import com.angio.angiobackend.api.user.service.UserService;
import com.angio.angiobackend.util.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("emailNotificationService")
    private final NotificationService<UUID> emailNotificationService;

    @Qualifier("pushNotificationService")
    private final NotificationService<UUID> pushNotificationService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SettingsRepository settingsRepository;
    private final UserMapper userMapper;
    private final SettingsMapper settingsMapper;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final AngioBackendProperties props;
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {id})));
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserEntityByEmail(@NonNull String email) {
        log.debug("findUserEntityByEmail() - start id: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
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
                .peek(entry -> settingsRepository.save(new Settings()
                        .setDarkThemeEnabled(props.getUserDefaultSettings().getDarkThemeEnabled())
                        .setUser(entry.getValue())))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        log.info("createUsers() - result: {}", passwordsAndNewUsers);

        log.trace("createUsers() - notify created users by email");
        for (Map.Entry<String, User> entry : passwordsAndNewUsers.entrySet()) {
            NewNotificationDto notification = new NewNotificationDto()
                    .setDate(new Date())
                    .setType(NotificationType.INFO)
                    .setTemplateName("registration.ftl")
                    .setSubject(new SubjectDto("Новая учетная запись Angio"))
                    .setDataModel(prepareRegistrationEmail(entry.getKey(), entry.getValue()));
            emailNotificationService.notifyUser(entry.getValue().getId(), notification);
        }

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
    @PreAuthorize("isAuthenticated()")
    public UserDetailsDto updateUser(@NonNull UpdateUserDto dto) {

        log.trace("updateUser() - start");
        User user = getUserFromContext();

        log.trace("updateUser() - merge dto to entity");
        userMapper.updateEntity(dto, user);

        log.trace("updateUser() - save updated user and return");
        return userMapper.toDetailedDto(userRepository.save(user));
    }

    /**
     * Get current user settings
     *
     * @return user settings
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public SettingsDto getSettings() {
        log.debug("getSettings() - start");
        return settingsMapper.toDto(getUserFromContext().getSettings());
    }

    /**
     * Update one or more user settings.
     *
     * @param dto settings dto
     * @return current updated settings
     */
    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public SettingsDto updateSettings(@NonNull SettingsDto dto) {

        log.debug("updateSettings() - start");
        Settings settings = getUserFromContext().getSettings();

        log.debug("updateSettings() - map updating settings to entity");
        settingsMapper.toEntity(dto, settings);

        log.debug("updateSettings() - save updated settings");
        settings = settingsRepository.save(settings);

        log.debug("updateSettings() - return changed settings");
        return settingsMapper.toDto(settings);
    }

    /**
     * Reset user settings to default
     *
     * @return current user settings
     */
    @Override
    public SettingsDto resetSettingsToDefault() {

        log.debug("resetSettingsToDefault() - start, set defaults");
        Settings settings = getUserFromContext().getSettings()
                .setDarkThemeEnabled(props.getUserDefaultSettings().getDarkThemeEnabled());

        log.debug("resetSettingsToDefault() - save updated settings");
        settings = settingsRepository.save(settings);

        log.debug("resetSettingsToDefault() - return changed settings");
        return settingsMapper.toDto(settings);
    }

    /**
     * Change user password.
     *
     * @param dto dto
     * @return updated user
     */
    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public UserDetailsDto changePassword(@NonNull ChangePasswordDto dto) {

        log.trace("changePassword() - start");
        User user = getUserFromContext();

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

    @Override
    @Transactional
    public void resetPassword(@NonNull String email) {

        log.debug("resetPassword() - start");
        User user = findUserEntityByEmail(email);
        String resetCode = PasswordUtils.generateNumberPassword(6);

        log.debug("resetPassword() - send email");
        NewNotificationDto notification = new NewNotificationDto()
                .setDate(new Date())
                .setType(NotificationType.INFO)
                .setTemplateName("reset-password.ftl")
                .setSubject(new SubjectDto("Восстановление пароля учетной записи в Angio"))
                .setDataModel(prepareResetPasswordEmail(resetCode, user));
        emailNotificationService.notifyUser(user.getId(), notification);

        log.debug("resetPassword() - disable user email={}", email);
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(resetCode));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resetUser(@NonNull UUID id, @NonNull ResetUserDto resetUser) {

        log.debug("resetUser() - start");
        User user = findUserEntityByUuid(id);

        log.debug("resetUser() - check the user needs resetting");
        if (user.isEnabled()
                || user.getFullName().getFirstname() == null
                || user.getFullName().getLastname() == null
                || user.getFullName().getPatronymic() == null) {
            throw new OperationException("User does not needs resetting now");
        }

        log.debug("resetUser() - match password");
        if (!passwordEncoder.matches(resetUser.getResetCode(), user.getPassword())) {
            throw new BadCredentialsException("Wrong resetting code");
        }

        log.debug("resetUser() - reset user and save");
        user.setPassword(passwordEncoder.encode(resetUser.getNewPassword()));
        user.setEnabled(true);

        userRepository.save(user);
        log.debug("resetUser() - end");
    }

    @Override
    @Transactional
    public UserDetailsDto enableUser(@NonNull UUID id, @NonNull EnableUserDto enableUser) {

        log.debug("enableUser() - start");
        User user = findUserEntityByUuid(id);

        log.debug("enableUser() - check the user needs resetting");
        if (user.isEnabled() || user.getFullName() != null) {
            throw new OperationException("User does not needs enabling now");
        }

        log.debug("resetUser() - match password");
        if (!passwordEncoder.matches(enableUser.getEnablingCode(), user.getPassword())) {
            throw new BadCredentialsException("Wrong enabling code");
        }

        log.debug("resetUser() - reset user and save");
        user.setFullName(new FullName()
                .setFirstname(enableUser.getFirstname())
                .setLastname(enableUser.getLastname())
                .setPatronymic(enableUser.getPatronymic()));
        user.setPassword(passwordEncoder.encode(enableUser.getNewPassword()));
        user.setEnabled(true);

        NewNotificationDto notification = new NewNotificationDto()
                .setDate(new Date())
                .setType(NotificationType.INFO)
                .setTemplateName("greeting.ftl")
                .setSubject(new SubjectDto("Добро пожаловать!"))
                .setDataModel(new GreetingPushDto()
                        .setFirstname(enableUser.getFirstname())
                        .setLastname(enableUser.getLastname()));
        pushNotificationService.notifyUser(user.getId(), notification);

        log.debug("resetUser() - end");
        return userMapper.toDetailedDto(userRepository.save(user));
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

    private AbstractEmailDto prepareRegistrationEmail(String password, User user) {
        String activationFormLink = UriComponentsBuilder.fromHttpUrl(props.getUi().getUserActivationFormLink())
                .buildAndExpand(user.getId()).toString();
        return new RegistrationEmailDto()
                .setEmail(user.getEmail())
                .setPassword(password)
                .setActivationFormLink(activationFormLink)
                .setPreview("Учетная запись Angio");
    }

    private AbstractEmailDto prepareResetPasswordEmail(String password, User user) {
        String resettingFormLink = UriComponentsBuilder.fromHttpUrl(props.getUi().getUserResettingFormLink())
                .buildAndExpand(user.getId()).toString();
        return new ResetPasswordDto()
                .setEmail(user.getEmail())
                .setPassword(password)
                .setResettingFormLink(resettingFormLink)
                .setPreview("Восстановление пароля учетной записи в Angio");
    }
}
