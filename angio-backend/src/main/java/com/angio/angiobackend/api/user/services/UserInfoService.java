package com.angio.angiobackend.api.user.services;


import com.angio.angiobackend.api.security.entities.UserEntity;
import com.angio.angiobackend.api.user.entities.UserInfoEntity;

import java.util.Date;

public interface UserInfoService {
    UserInfoEntity findByUser(UserEntity user) throws Exception;
    void update(long userInfo_id, String username, String firstname, String lastname, Date modified_date) throws Exception;
}