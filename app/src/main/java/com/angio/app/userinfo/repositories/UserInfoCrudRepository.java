package com.angio.app.userinfo.repositories;

import com.angio.app.security.entities.UserEntity;
import com.angio.app.userinfo.entities.UserInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInfoCrudRepository extends CrudRepository<UserInfoEntity, Long>{
    List<UserInfoEntity> findByUser(UserEntity user);
}