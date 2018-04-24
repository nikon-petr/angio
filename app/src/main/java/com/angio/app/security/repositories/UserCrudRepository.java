package com.angio.app.security.repositories;


import com.angio.app.security.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserCrudRepository extends CrudRepository<UserEntity, String> {
    List<UserEntity> findByUsername(String username);
    List<UserEntity> findByEnabled(boolean enabled);
}