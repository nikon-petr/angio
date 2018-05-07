package com.angio.server.security.services;

import com.angio.server.security.entities.AuthorityEntity;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.security.repositories.AuthorityCrudRepository;
import com.angio.server.security.repositories.UserCrudRepository;
import com.angio.server.user.entities.UserInfoEntity;
import com.angio.server.user.repositories.UserInfoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserCrudRepository userRepository;
    private final UserInfoCrudRepository userInfoCrudRepository;
    private final AuthorityCrudRepository authorityCrudRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserCrudRepository userRepository,
            UserInfoCrudRepository userInfoCrudRepository,
            AuthorityCrudRepository authorityCrudRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userInfoCrudRepository = userInfoCrudRepository;
        this.authorityCrudRepository = authorityCrudRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = findByUsername(s);
        return userEntity;
    }

    @Override
    public UserEntity findByUsername(String username) throws UsernameNotFoundException{
        UserEntity user = userRepository.findByUsername(username).stream()
                .findFirst()
                .orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("Username does not exist.");
        }
        return user;
    }

    @Override
    @Transactional
    public void registerNewUser(
            String username,
            String password,
            String firstname,
            String lastname,
            Date modified_date) throws UsernameExistsException {
        if(userRepository.exists(username)){
            throw new UsernameExistsException("There is user with the same username");
        }

        UserEntity userEntity = new UserEntity(username, passwordEncoder.encode(password), true, new Date());
        userEntity = userRepository.save(userEntity);

        authorityCrudRepository.save(new AuthorityEntity(userEntity, "ROLE_USER"));

        UserInfoEntity userInfoEntity = new UserInfoEntity(
                userRepository.findByUsername(username).get(0),
                firstname,
                lastname,
                modified_date);
        userInfoCrudRepository.save(userInfoEntity);
    }
}
