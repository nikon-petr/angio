package com.angio.app.security.services;

import com.angio.app.security.entities.AuthorityEntity;
import com.angio.app.security.entities.UserEntity;
import com.angio.app.security.repositories.AuthorityCrudRepository;
import com.angio.app.security.repositories.UserCrudRepository;
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
    private final AuthorityCrudRepository authorityCrudRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserCrudRepository userRepository,
            AuthorityCrudRepository authorityCrudRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityCrudRepository = authorityCrudRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(s).get(0);
        if(userEntity == null) throw new UsernameNotFoundException("Username does not exist.");
        return userEntity;
    }

    @Override
    public UserEntity findByUsername(String username) throws UsernameNotFoundException{
        UserEntity user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("Username does not exist.");
        }
        return user;
    }

    @Override
    public UserEntity registerNewUser(String username, String password) throws UsernameExistsException {
        if(userRepository.exists(username)){
            throw new UsernameExistsException("There is user with the same username");
        }
        UserEntity userEntity = new UserEntity(username, passwordEncoder.encode(password), true, new Date());
        userEntity = userRepository.save(userEntity);
        authorityCrudRepository.save(new AuthorityEntity(userEntity, "ROLE_USER"));
        return userEntity;
    }
}
