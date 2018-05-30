package com.angio.server.security.repositories;

import com.angio.server.security.entities.TokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenCrudRepository extends CrudRepository<TokenEntity, Long> {
    List<TokenEntity> findByUserUsernameAndEnabledTrueOrderByIssuedAtDesc(String username);
    TokenEntity findOneByIdAndUserUsername(long id, String username);
    List<TokenEntity> findById(long id);
    List<TokenEntity> findByEnabled(boolean enabled);
}
