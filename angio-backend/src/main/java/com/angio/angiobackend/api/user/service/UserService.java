package com.angio.angiobackend.api.user.service;

import com.angio.angiobackend.api.common.dto.Response;
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
import com.angio.angiobackend.api.user.dto.UserShortItemDto;
import com.angio.angiobackend.api.user.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User findUserEntityByUuid(UUID id);

    User findUserEntityByEmail(String email);

    List<NewUserDto> createUsers(List<NewUserDto> dtos);

    Page<UserShortItemDto> filterUsersByQueryString(
            String search,
            Boolean enabled,
            Boolean locked,
            Long organizationId,
            Pageable pageable);

    Response registerUser(RegisterUserDto dto);

    UserDetailsDto getUserById(UUID id);

    UserDto getCurrentUser();

    UserDetailsDto updateUser(UpdateUserDto dto);

    SettingsDto getSettings();

    SettingsDto updateSettings(SettingsDto dto);

    SettingsDto resetSettingsToDefault();

    UserDetailsDto changePassword(ChangePasswordDto dto);

    void resetPassword(String email);

    void resetUser(UUID id, ResetUserDto resetUser);

    UserDetailsDto enableUser(UUID id, EnableUserDto enableUser);

    UserDetailsDto changeUserLocked(UUID id, UserLockedDto dto);
}
