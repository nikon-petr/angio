package com.angio.server.user.repositories;

import com.angio.server.security.entities.UserEntity;
import com.angio.server.user.entities.UserInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInfoCrudRepository extends CrudRepository<UserInfoEntity, Long>{
    List<UserInfoEntity> findByUser(UserEntity user);
}