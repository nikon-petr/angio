package com.angio.app.security.services;

import com.angio.app.security.entities.TokenEntity;
import com.angio.app.security.entities.UserEntity;

public interface TokenService {
    TokenEntity findByUser(UserEntity userEntity);
    TokenEntity findByToken(String token);
    TokenEntity putToken(UserEntity userEntity, String token);
    TokenEntity revokeToken(long id);
}
