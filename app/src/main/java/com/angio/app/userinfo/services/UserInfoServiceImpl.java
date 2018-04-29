package com.angio.app.userinfo.services;


import com.angio.app.security.entities.UserEntity;
import com.angio.app.security.repositories.UserCrudRepository;
import com.angio.app.userinfo.entities.UserInfoEntity;
import com.angio.app.userinfo.repositories.UserInfoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    UserInfoCrudRepository userInfoCrudRepository;
    UserCrudRepository userCrudRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoCrudRepository userInfoCrudRepository,
                               UserCrudRepository userCrudRepository) {
        this.userInfoCrudRepository = userInfoCrudRepository;
        this.userCrudRepository = userCrudRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoEntity findByUser(UserEntity user) throws Exception {
        UserInfoEntity userInfo = userInfoCrudRepository.findByUser(user).stream()
                .findFirst()
                .orElse(null);
        if(userInfo == null){
            throw new UsernameNotFoundException("Username does not exist.");
        }

        return userInfo;
    }

    @Override
    public void update(long userInfo_id, String username, String firstname, String lastname, Date modified_date) throws Exception {
        UserInfoEntity userInfo = userInfoCrudRepository.findOne(userInfo_id);
        userInfo.setFirstname(firstname);
        userInfo.setLastname(lastname);
        userInfo.setModified_date(modified_date);
        userInfo.setUser(userCrudRepository.findOne(username));
        userInfoCrudRepository.save(userInfo);
    }
}