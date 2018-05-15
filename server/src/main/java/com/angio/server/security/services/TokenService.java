package com.angio.server.security.services;

import com.angio.server.security.entities.TokenEntity;
import com.angio.server.security.entities.UserEntity;

import java.util.Date;

public interface TokenService {
    TokenEntity findByUser(UserEntity userEntity);
    TokenEntity findById(long id);
    TokenEntity putToken(UserEntity userEntity, String os, String browser, String deviceType);
    TokenEntity putTokenExpiration(TokenEntity tokenEntity, Date expiration);
    TokenEntity revokeToken(long id);
}
