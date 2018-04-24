package com.angio.app.security.repositories;

import com.angio.app.security.entities.TokenEntity;
import com.angio.app.security.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenCrudRepository extends CrudRepository<TokenEntity, Long> {
    List<TokenEntity> findByUsername(UserEntity user);
    List<TokenEntity> findById(long id);
    List<TokenEntity> findByToken(String token);
    List<TokenEntity> findByEnabled(boolean enabled);
}
