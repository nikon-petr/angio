package com.angio.angiobackend.security.services;

import com.angio.angiobackend.security.entities.TokenEntity;
import com.angio.angiobackend.security.entities.UserEntity;

import java.util.Date;
import java.util.List;

public interface TokenService {
    List<TokenEntity> findByUsername(String username);
    TokenEntity findById(long id);
    TokenEntity putToken(UserEntity userEntity, String os, String browser, String deviceType);
    TokenEntity putTokenExpirationAndIssuedAt(TokenEntity tokenEntity, Date expiration, Date issuedAt);
    TokenEntity revokeTokenAsAdmin(long id);
    TokenEntity revokeTokenAsUser(long id, String username);
}
