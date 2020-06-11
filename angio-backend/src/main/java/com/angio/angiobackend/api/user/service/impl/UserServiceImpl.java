package com.angio.angiobackend.api.user.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.dto.AbstractEmailDto;
import com.angio.angiobackend.api.common.dto.Response;
import com.angio.angiobackend.api.common.exception.OperationException;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.common.mapper.FullNameMapper;
import com.angio.angiobackend.api.notification.dto.NewNotificationDto;
import com.angio.angiobackend.api.notification.dto.SubjectDto;
import com.angio.angiobackend.api.notification.service.NotificationService;
import com.angio.angiobackend.api.notification.type.NotificationType;
import com.angio.angiobackend.api.organization.entity.Organization;
import com.angio.angiobackend.api.organization.repository.OrganizationRepository;
import com.angio.angiobackend.api.security.entity.Role;
import com.angio.angiobackend.api.security.repository.RoleRepository;
import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.EnableUserDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.RegisterUserDto;
import com.angio.angiobackend.api.user.dto.ResetUserDto;
import com.angio.angiobackend.api.user.dto.SettingsDto;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserDto;
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
import com.angio.angiobackend.api.user.service.CurrentUserResolver;
import com.angio.angiobackend.api.user.service.UserService;
import com.angio.angiobackend.api.user.specification.UserSpecification;
import com.angio.angiobackend.init.Roles;
import com.angio.angiobackend.util.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final TransactionTemplate transactionTemplate;

    @Qualifier("emailNotificationService")
    private final NotificationService<UUID> emailNotificationService;

    @Qualifier("pushNotificationService")
    private final NotificationService<UUID> pushNotificationService;

    private final CurrentUserResolver currentUserResolver;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SettingsRepository settingsRepository;
    private final OrganizationRepository organizationRepository;
    private final UserMapper userMapper;
    private final SettingsMapper settingsMapper;
    private final FullNameMapper fullNameMapper;
    private final UserSpecification userSpecification;
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
     * Create new user accounts with property enabled=false.
     *
     * @param dtos user data to create
     * @return created user data
     */
    @Override
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public List<UserDetailsDto> createUsers(@NonNull List<NewUserDto> dtos) {

        Map<String, User> passwordsAndCreatedUsers = transactionTemplate.execute((status) -> {

            log.debug("createUsers() - start");
            if (dtos.size() == 0) {
                log.debug("createUsers() - empty user list");
                return Collections.emptyMap();
            }

            log.debug("createUsers() - checking all requested email is unique");
            checkEmailUnique(dtos);

            log.debug("createUsers() - fetch needed roles");
            List<Role> newUserRoles = findRolesForUsers(dtos);

            log.debug("createUsers() - fetch needed organizations");
            List<Organization> newUserOrganizations = findOrganizationsForUsers(dtos);

            log.debug("createUsers() - checking that creator is owner of requested roles");
            checkAllowedRoles(newUserRoles);

            log.debug("createUsers() - generate passwords and save users");
            Map<String, User> passwordsAndNewUsers = dtos.stream()
                    .map(dto -> new AbstractMap.SimpleEntry<>(PasswordUtils.generateNumberPassword(6), new User()
                            .setEmail(dto.getEmail())
                            .setEnabled(false)
                            .setLocked(false)
                            .setRoles(newUserRoles.stream()
                                    .filter(role -> dto.getRoleIds().contains(role.getId()))
                                    .collect(Collectors.toSet()))
                            .setOwnedRolesToManage(newUserRoles.stream()
                                    .filter(role -> dto.getOwnedRoleToManageIds().contains(role.getId()))
                                    .collect(Collectors.toSet()))
                            .setOrganization(newUserOrganizations.stream()
                                    .filter(organization -> organization.getId().equals(dto.getOrganizationId()))
                                    .findFirst()
                                    .orElse(null))))
                    .peek(entry -> entry.getValue().setPassword(passwordEncoder.encode(entry.getKey())))
                    .peek(entry -> entry.setValue(userRepository.save(entry.getValue())))
                    .peek(entry -> settingsRepository.save(settingsMapper.toNewEntity(props.getUserDefaultSettings())
                            .setUser(entry.getValue())))
                    .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
            log.info("createUsers() - result: {}", passwordsAndNewUsers);

            userMapper.toDetailedDto(passwordsAndNewUsers.values());
            return passwordsAndNewUsers;
        });

        log.debug("createUsers() - notify created users by email");
        for (Map.Entry<String, User> entry : passwordsAndCreatedUsers.entrySet()) {
            NewNotificationDto notification = new NewNotificationDto()
                    .setDate(new Date())
                    .setType(NotificationType.INFO)
                    .setTemplateName("registration.ftl")
                    .setSubject(new SubjectDto("Новая учетная запись Angio"))
                    .setDataModel(prepareRegistrationEmail(entry.getKey(), entry.getValue()));
            emailNotificationService.notifyUser(entry.getValue().getId(), notification);
        }

        log.debug("createUsers() - end");
        return transactionTemplate.execute((status) -> userMapper.toDetailedDto(passwordsAndCreatedUsers.values()));
    }

    /**
     * Filter users by query string matching any one or more fields.
     *
     * @param search query string
     * @param enabled user enabled
     * @param locked user locked
     * @param organizationId organization id
     * @param roleIds role id list
     * @param ownedRoleIds owned role id list
     * @param pageable page request
     * @return page of filtered users
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public Page<UserDetailsDto> filterUsersByQueryString(
            String search,
            Boolean enabled,
            Boolean locked,
            Long organizationId,
            List<Long> roleIds,
            List<Long> ownedRoleIds,
            Pageable pageable) {
        log.debug("filterUsersByQueryString() - start");

        log.debug("filterUsersByQueryString() - build user specification");
        Specification<User> specs = userSpecification.getUserFilter(search)
                .and(userSpecification.userEnabled(enabled))
                .and(userSpecification.userLocked(locked))
                .and(userSpecification.userOrganizationId(organizationId))
                .and(userSpecification.userRoleIds(roleIds))
                .and(userSpecification.userOwnedRoleIds(ownedRoleIds))
                .and(userSpecification.distinct());

        log.debug("filterUsersByQueryString() - filter user");
        Page<User> analyseInfoEntityPage = userRepository.findAll(specs, pageable);

        log.debug("filterUsersByQueryString() - map and return user page");
        return analyseInfoEntityPage.map(userMapper::toDetailedDto);
    }

    /**
     * Register new user with role SINGLE_DOCTOR and no organization set.
     *
     * @param dto registration data
     * @return success response
     */
    @Override
    @Transactional
    public Response registerUser(RegisterUserDto dto) {
        log.debug("registerUser() - start");

        log.debug("registerUser() - checking email is unique");
        checkEmailUnique(dto.getEmail());

        log.debug("createUsers() - fetch SINGLE_DOCTOR role");
        Role doctorRole = roleRepository.findByName(Roles.SINGLE_DOCTOR.name())
                .orElseThrow(RuntimeException::new);

        log.debug("createUsers() - generate password");
        String password = PasswordUtils.generateNumberPassword(6);

        User newUser = transactionTemplate.execute((status) -> {
            User userToSave = userRepository.save(new User()
                    .setEmail(dto.getEmail())
                    .setEnabled(false)
                    .setLocked(false)
                    .setRoles(Collections.singleton(doctorRole))
                    .setOrganization(null)
                    .setPassword(passwordEncoder.encode(password)));

            log.debug("createUsers() - create user settings");
            settingsRepository.save(settingsMapper.toNewEntity(props.getUserDefaultSettings()).setUser(userToSave));

            return userToSave;
        });

        log.debug("createUsers() - send registration email");
        NewNotificationDto notification = new NewNotificationDto()
                .setDate(new Date())
                .setType(NotificationType.INFO)
                .setTemplateName("registration.ftl")
                .setSubject(new SubjectDto("Регистрация в Angio"))
                .setDataModel(prepareRegistrationEmail(password, newUser));
        emailNotificationService.notifyUser(newUser.getId(), notification);

        log.debug("createUsers() - end");
        return Response.success(null);
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
     * Get current user user.
     *
     * @return user data
     */
    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public UserDto getCurrentUser() {
        return userMapper.toUserDto(currentUserResolver.getCurrentUser());
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

        log.debug("updateUser() - start");
        User user = currentUserResolver.getCurrentUser();

        log.debug("updateUser() - merge dto to entity");
        userMapper.updateEntity(dto, user);

        log.debug("updateUser() - save updated user and return");
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
        return settingsMapper.toDto(currentUserResolver.getCurrentUser().getSettings());
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
        Settings settings = currentUserResolver.getCurrentUser().getSettings();

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
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public SettingsDto resetSettingsToDefault() {

        log.debug("resetSettingsToDefault() - start, set defaults");
        Settings settings = currentUserResolver.getCurrentUser().getSettings()
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

        log.debug("changePassword() - start");
        User user = currentUserResolver.getCurrentUser();

        log.debug("changePassword() - check old password");
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password check failure");
        }

        log.debug("changePassword() - save new password");
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user = userRepository.save(user);

        log.debug("changePassword() - end");
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
            throw new BadCredentialsException(msa.getMessage("errors.api.user.wrongEnablingCode"));
        }

        log.debug("resetUser() - reset user and save");
        user.setFullName(fullNameMapper.toEntity(enableUser.getFullName()));
        user.setPassword(passwordEncoder.encode(enableUser.getNewPassword()));
        user.setEnabled(true);

        NewNotificationDto notification = new NewNotificationDto()
                .setDate(new Date())
                .setType(NotificationType.INFO)
                .setTemplateName("greeting.ftl")
                .setSubject(new SubjectDto("Добро пожаловать!"))
                .setDataModel(new GreetingPushDto()
                        .setFirstname(enableUser.getFullName().getFirstname())
                        .setLastname(enableUser.getFullName().getLastname()));
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

        log.debug("changeUserLocked() - start, id: {}", id);
        User user = findUserEntityByUuid(id);

        log.debug("changeUserLocked() - change and save locked");
        user.setLocked(dto.getLocked());
        user = userRepository.save(user);

        log.debug("changeUserLocked() - end");
        return userMapper.toDetailedDto(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public UserDetailsDto changeUserRoles(@NotNull UUID id, @NotNull List<Long> roleIds) {

        log.debug("changeUserRoles() - start, id: {}", id);
        log.debug("changeUserRoles() - find user");
        User user = findUserEntityByUuid(id);

        List<Role> changedRoles = roleRepository.findAllById(roleIds);

        if (changedRoles.size() == 0) {
            throw new ResourceNotFoundException(
                    msa.getMessage("errors.api.user.rolesNotFound", new Object[] {roleIds}));
        }

        log.debug("changeUserRoles() - checking that change requester is owner of requested roles");
        Set<Long> userRolesIdsFromDb = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        Set<Long> newRolesIdListFromDb = changedRoles.stream().map(Role::getId).collect(Collectors.toSet());
        checkAllowedRoles(newRolesIdListFromDb, userRolesIdsFromDb);

        log.debug("changeUserRoles() - change and save");
        user.setRoles(new HashSet<>(changedRoles));
        user = userRepository.save(user);

        log.debug("changeUserRoles() - end");
        return userMapper.toDetailedDto(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public UserDetailsDto changeUserOwnedRoles(@NotNull UUID id, @NotNull List<Long> roleIds) {
        log.debug("changeUserOwnedRoles() - start, id: {}", id);
        log.debug("changeUserOwnedRoles() - find user");
        User user = findUserEntityByUuid(id);

        List<Role> changedRoles = roleRepository.findAllById(roleIds);

        if (changedRoles.size() == 0) {
            log.debug("changeUserOwnedRoles() - clear user owned roles to manage");
            user.setOwnedRolesToManage(Collections.emptySet());
            user = userRepository.save(user);

            log.debug("changeUserOwnedRoles() - end");
            return userMapper.toDetailedDto(user);
        }

        log.debug("changeUserOwnedRoles() - checking that change requester is owner of requested roles");
        Set<Long> userRolesIdsFromDb = user.getOwnedRolesToManage().stream().map(Role::getId).collect(Collectors.toSet());
        Set<Long> newRolesIdListFromDb = changedRoles.stream().map(Role::getId).collect(Collectors.toSet());
        checkAllowedRoles(newRolesIdListFromDb, userRolesIdsFromDb);

        log.debug("changeUserOwnedRoles() - change and save");
        user.setOwnedRolesToManage(new HashSet<>(changedRoles));
        user = userRepository.save(user);

        log.debug("changeUserOwnedRoles() - end");
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

    private void checkEmailUnique(String email) {
        Optional<User> existingUsers = userRepository.findByEmail(email);
        if (existingUsers.isPresent()) {
            throw new OperationException(
                    msa.getMessage("errors.api.user.emailNotUnique", new Object[]{email}));
        }
    }

    private List<Role> findRolesForUsers(List<NewUserDto> dtos) {
        Stream<Long> rolesIds = dtos.stream()
                .map(NewUserDto::getRoleIds)
                .flatMap(Collection::stream);
        Stream<Long> ownedRolesIds = dtos.stream()
                .map(NewUserDto::getOwnedRoleToManageIds)
                .flatMap(Collection::stream);
        List<Long> allRolesIds = Stream.concat(rolesIds, ownedRolesIds)
                .distinct()
                .collect(Collectors.toList());
        List<Role> roles = roleRepository.findAllById(allRolesIds);

        if (allRolesIds.size() != roles.size()) {
            allRolesIds.removeAll(roles.stream().map(Role::getId).collect(Collectors.toList()));
            throw new OperationException(
                    msa.getMessage("errors.api.user.rolesForUserNotFound", new Object[]{rolesIds}));
        }

        return roles;
    }

    private List<Organization> findOrganizationsForUsers(List<NewUserDto> dtos) {
        List<Long> organizationsIds = dtos.stream()
                .filter(dto -> dto.getOrganizationId() != null)
                .map(NewUserDto::getOrganizationId)
                .distinct()
                .collect(Collectors.toList());
        List<Organization> organizations = organizationRepository.findAllById(organizationsIds);

        if (organizationsIds.size() != organizations.size()) {
            organizationsIds.removeAll(organizations.stream().map(Organization::getId).collect(Collectors.toList()));
            throw new OperationException(
                    msa.getMessage("errors.api.user.organizationsForUserNotFound", new Object[]{organizationsIds}));
        }

        return organizations;
    }

    private void checkAllowedRoles(List<Role> roles) {
        User creator = currentUserResolver.getCurrentUser();

        if (!creator.getOwnedRolesToManage().containsAll(roles)) {
            List<String> ownedRolesIds = creator.getOwnedRolesToManage().stream()
                    .map(Role::getDescription)
                    .collect(Collectors.toList());
            throw new OperationException(
                    msa.getMessage("errors.api.user.rolesNotOwnedUser", new Object[] {ownedRolesIds}));
        }
    }

    private void checkAllowedRoles(Set<Long> newRoleIdList, Set<Long> userRolesFromDbIds) {
        User creator = currentUserResolver.getCurrentUser();
        Collection roleListDiffIds = CollectionUtils.disjunction(newRoleIdList, userRolesFromDbIds);
        log.debug("checkAllowedRoles() - new role list: {}", newRoleIdList);
        log.debug("checkAllowedRoles() - user roles from db: {}", userRolesFromDbIds);
        log.debug("checkAllowedRoles() - role list diff: {}", roleListDiffIds);
        Collection<Long> ownedRoleIds = creator.getOwnedRolesToManage().stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        if (!ownedRoleIds.containsAll(roleListDiffIds)) {
            List<String> ownedRolesDescriptions = creator.getOwnedRolesToManage().stream()
                    .map(Role::getDescription)
                    .collect(Collectors.toList());
            throw new OperationException(
                    msa.getMessage("errors.api.user.rolesNotOwnedUser", new Object[] {ownedRolesDescriptions}));
        }
    }

    private AbstractEmailDto prepareRegistrationEmail(String password, User user) {
        String activationFormLink = UriComponentsBuilder.fromHttpUrl(props.getBaseUrl())
                .path(props.getUi().getUserActivationFormPath())
                .buildAndExpand(user.getId()).toString();
        return new RegistrationEmailDto()
                .setEmail(user.getEmail())
                .setPassword(password)
                .setActivationFormLink(activationFormLink)
                .setPreview("Учетная запись Angio");
    }

    private AbstractEmailDto prepareResetPasswordEmail(String password, User user) {
        String resettingFormLink = UriComponentsBuilder.fromHttpUrl(props.getBaseUrl())
                .path(props.getUi().getUserResettingFormPath())
                .buildAndExpand(user.getId()).toString();
        return new ResetPasswordDto()
                .setEmail(user.getEmail())
                .setPassword(password)
                .setResettingFormLink(resettingFormLink)
                .setPreview("Восстановление пароля учетной записи в Angio");
    }
}
