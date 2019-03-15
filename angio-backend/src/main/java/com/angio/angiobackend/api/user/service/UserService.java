package com.angio.angiobackend.api.user.service;

import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserLockedDto;
import com.angio.angiobackend.api.user.entities.User;
import freemarker.template.TemplateException;
import lombok.NonNull;
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

    @Transactional
    UserDetailsDto getUserById(UUID id);

    @Transactional
    UserDetailsDto updateUser(@NonNull UUID id, @NonNull UpdateUserDto dto);

    @Transactional
    UserDetailsDto changePassword(@NonNull UUID id, @NonNull ChangePasswordDto dto);

    @Transactional
    UserDetailsDto changeUserLocked(@NonNull UUID id, @NonNull UserLockedDto dto);
}
