package com.angio.app.security.services;

import com.angio.app.security.entities.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserEntity findByUsername(String username) throws UsernameNotFoundException;
    UserEntity registerNewUser(String username, String password) throws UsernameExistsException;
}
