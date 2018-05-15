package com.angio.server.security.repositories;

import com.angio.server.security.entities.TokenEntity;
import com.angio.server.security.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenCrudRepository extends CrudRepository<TokenEntity, Long> {
    List<TokenEntity> findByUsername(UserEntity user);
    List<TokenEntity> findById(long id);
    List<TokenEntity> findByEnabled(boolean enabled);
}
