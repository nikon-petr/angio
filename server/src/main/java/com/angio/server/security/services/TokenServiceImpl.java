package com.angio.server.security.services;

import com.angio.server.security.entities.TokenEntity;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.security.repositories.TokenCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("tokenService")
@Transactional
public class TokenServiceImpl implements TokenService {

    private final TokenCrudRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenCrudRepository tokenCrudRepository) {
        this.tokenRepository = tokenCrudRepository;
    }


    @Override
    public TokenEntity findByUser(UserEntity user) {
        TokenEntity tokenEntity = tokenRepository.findByUsername(user).stream()
                .findFirst()
                .orElse(null);
        return tokenEntity;
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
        TokenEntity tokenEntity = new TokenEntity(user, true, os, browser, device, null);
        tokenEntity = tokenRepository.save(tokenEntity);
        return tokenEntity;
    }

    @Override
    public TokenEntity putTokenExpiration(TokenEntity tokenEntity, Date expiration) {
        tokenEntity.setExpiration(expiration);
        return tokenRepository.save(tokenEntity);
    }

    @Override
    public TokenEntity revokeToken(long id) {
        TokenEntity tokenEntity = tokenRepository.findOne(id);
        if (tokenEntity == null){
            throw new TokenException("Token does not exists");
        }
        tokenEntity.setEnabled(false);
        tokenRepository.save(tokenEntity);
        return tokenEntity;
    }
}
