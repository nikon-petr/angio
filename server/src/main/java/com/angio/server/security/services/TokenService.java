package com.angio.server.security.services;

import com.angio.server.security.entities.TokenEntity;
import com.angio.server.security.entities.UserEntity;

public interface TokenService {
    TokenEntity findByUser(UserEntity userEntity);
    TokenEntity findByToken(String token);
    TokenEntity putToken(UserEntity userEntity, String token);
    TokenEntity revokeToken(long id);
}
