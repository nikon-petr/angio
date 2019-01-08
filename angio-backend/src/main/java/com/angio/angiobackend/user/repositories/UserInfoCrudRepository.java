package com.angio.angiobackend.user.repositories;

import com.angio.angiobackend.security.entities.UserEntity;
import com.angio.angiobackend.user.entities.UserInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInfoCrudRepository extends CrudRepository<UserInfoEntity, Long>{
    List<UserInfoEntity> findByUser(UserEntity user);
}