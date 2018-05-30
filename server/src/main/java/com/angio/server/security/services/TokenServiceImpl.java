package com.angio.server.security.services;

import com.angio.server.security.entities.TokenEntity;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.security.repositories.TokenCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("tokenService")
@Transactional
public class TokenServiceImpl implements TokenService {

    private final TokenCrudRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenCrudRepository tokenCrudRepository) {
        this.tokenRepository = tokenCrudRepository;
    }


    @Override
    public List<TokenEntity> findByUsername(String username) {
        return tokenRepository.findByUserUsernameAndEnabledTrueOrderByIssuedAtDesc(username);
    }

    @Override
    public TokenEntity findById(long id) {
        TokenEntity tokenEntity = tokenRepository.findOne(id);
        if (tokenEntity == null){
            throw new TokenException(String.format("Token with id: %s not found", id));
        }
        return tokenRepository.findOne(id);
    }

    @Override
    @Transactional
    public TokenEntity putToken(UserEntity user, String os, String browser, String device) {
        TokenEntity tokenEntity = new TokenEntity(user, true, os, browser, device);
        tokenEntity = tokenRepository.save(tokenEntity);
        return tokenEntity;
    }

    @Override
    public TokenEntity putTokenExpirationAndIssuedAt(TokenEntity tokenEntity, Date expiration, Date issuedAt) {
        tokenEntity.setExpiration(expiration);
        tokenEntity.setIssuedAt(issuedAt);
        return tokenRepository.save(tokenEntity);
    }

    @Override
    public TokenEntity revokeTokenAsAdmin(long id) {
        TokenEntity tokenEntity = tokenRepository.findOne(id);
        if (tokenEntity == null){
            throw new TokenException("Token does not exists");
        }
        tokenEntity.setEnabled(false);
        tokenRepository.save(tokenEntity);
        return tokenEntity;
    }

    @Override
    public TokenEntity revokeTokenAsUser(long id, String username) {
        TokenEntity tokenEntity = tokenRepository.findOneByIdAndUserUsername(id, username);
        if (tokenEntity == null){
            throw new TokenException("Token does not exists");
        }
        tokenEntity.setEnabled(false);
        tokenRepository.save(tokenEntity);
        return tokenEntity;
    }
}
