package com.angio.angiobackend.api.user.service;

import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.UserBaseDto;
import com.angio.angiobackend.api.user.entities.User;
import lombok.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserService {

    @Transactional
    User findUserEntityByUuid(@NonNull UUID id);

    @Transactional
    User findUserEntityByEmail(@NonNull String email);

    @Transactional
    User getUserFromContext();

    @Transactional
    UserBaseDto updateUser(@NonNull UUID id, @NonNull UserBaseDto dto);

    @Transactional
    String changePassword(@NonNull UUID id, @NonNull ChangePasswordDto dto);
}
