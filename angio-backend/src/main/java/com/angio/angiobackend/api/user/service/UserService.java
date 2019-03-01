package com.angio.angiobackend.api.user.service;

import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.UserBaseDto;
import com.angio.angiobackend.api.user.dto.UserDetailedDto;
import com.angio.angiobackend.api.user.dto.UserLockedDto;
import com.angio.angiobackend.api.user.entities.User;
import lombok.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    UserDetailedDto getUserById(UUID id);

    @Transactional
    UserDetailedDto updateUser(@NonNull UUID id, @NonNull UserBaseDto dto);

    @Transactional
    UserDetailedDto changePassword(@NonNull UUID id, @NonNull ChangePasswordDto dto);

    @Transactional
    UserDetailedDto changeUserLocked(@NonNull UUID id, @NonNull UserLockedDto dto);
}
