package com.angio.angiobackend.api.security.service;

import com.angio.angiobackend.api.security.entity.User;
import lombok.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserService {

    @Transactional
    User findUserEntityByEmail(@NonNull String email);
}
