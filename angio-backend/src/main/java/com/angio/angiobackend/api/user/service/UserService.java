package com.angio.angiobackend.api.user.service;

import com.angio.angiobackend.api.common.dto.Response;
import com.angio.angiobackend.api.user.dto.EnableUserDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.RegisterUserDto;
import com.angio.angiobackend.api.user.dto.ResetUserDto;
import com.angio.angiobackend.api.user.dto.SettingsDto;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserDto;
import com.angio.angiobackend.api.user.dto.UserLockedDto;
import com.angio.angiobackend.api.user.dto.UserShortItemDto;
import com.angio.angiobackend.api.user.entities.User;
import freemarker.template.TemplateException;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserService {

    @Transactional
    User findUserEntityByUuid(@NonNull UUID id);

    @Transactional
    User findUserEntityByEmail(@NonNull String email);

    @Transactional
    User getUserFromContext();

    UUID getUserIdFromContext();

    @Transactional
    List<NewUserDto> createUsers(List<NewUserDto> dtos);

    @Transactional(readOnly = true)
    Page<UserShortItemDto> filterUsersByQueryString(
            String search,
            Boolean enabled,
            Boolean locked,
            Long organizationId,
            Pageable pageable);

    @Transactional
    Response registerUser(RegisterUserDto dto);

    @Transactional
    UserDetailsDto getUserById(UUID id);

    @Transactional
    UserDto getCurrentUser();

    @Transactional
    UserDetailsDto updateUser(@NonNull UpdateUserDto dto);

    @Transactional
    SettingsDto getSettings();

    @Transactional
    SettingsDto updateSettings(@NonNull SettingsDto dto);

    @Transactional
    SettingsDto resetSettingsToDefault();

    @Transactional
    UserDetailsDto changePassword(@NonNull ChangePasswordDto dto);

    @Transactional
    void resetPassword(@NonNull String email);

    @Transactional
    void resetUser(@NonNull UUID id, @NonNull ResetUserDto resetUser);

    @Transactional
    UserDetailsDto enableUser(@NonNull UUID id, @NonNull EnableUserDto enableUser);

    @Transactional
    UserDetailsDto changeUserLocked(@NonNull UUID id, @NonNull UserLockedDto dto);
}
