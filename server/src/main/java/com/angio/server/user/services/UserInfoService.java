package com.angio.server.user.services;


import com.angio.server.security.entities.UserEntity;
import com.angio.server.user.entities.UserInfoEntity;

import java.util.Date;

public interface UserInfoService {
    UserInfoEntity findByUser(UserEntity user) throws Exception;
    void update(long userInfo_id, String username, String firstname, String lastname, Date modified_date) throws Exception;
}