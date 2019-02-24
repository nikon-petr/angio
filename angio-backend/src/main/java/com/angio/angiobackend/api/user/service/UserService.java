package com.angio.angiobackend.api.user.service;

import com.angio.angiobackend.api.user.entities.User;
import lombok.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserService {

    @Transactional
    User findUserEntityByUuid(@NonNull String uuid);

    @Transactional
    User findUserEntityByEmail(@NonNull String email);

    @Transactional
    User getUserFromContext();
}
