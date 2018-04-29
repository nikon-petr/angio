package com.angio.app.userinfo.services;


import com.angio.app.security.entities.UserEntity;
import com.angio.app.userinfo.entities.UserInfoEntity;

import java.util.Date;

public interface UserInfoService {
    UserInfoEntity findByUser(UserEntity user) throws Exception;
    void update(long userInfo_id, String username, String firstname, String lastname, Date modified_date) throws Exception;
}