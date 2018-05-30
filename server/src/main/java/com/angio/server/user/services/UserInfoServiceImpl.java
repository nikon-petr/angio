package com.angio.server.user.services;


import com.angio.server.security.entities.UserEntity;
import com.angio.server.security.repositories.UserRepository;
import com.angio.server.user.entities.UserInfoEntity;
import com.angio.server.user.repositories.UserInfoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    UserInfoCrudRepository userInfoCrudRepository;
    UserRepository userRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoCrudRepository userInfoCrudRepository,
                               UserRepository userRepository) {
        this.userInfoCrudRepository = userInfoCrudRepository;
        this.userRepository = userRepository;
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
        userInfo.setUser(userRepository.findOne(username));
        userInfoCrudRepository.save(userInfo);
    }
}