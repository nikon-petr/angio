package com.angio.server.security.services;

import com.angio.server.security.entities.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

public interface UserService {
    UserEntity findByUsername(String username) throws UsernameNotFoundException;
    void registerNewUser(String username,
                               String password,
                               String firstname,
                               String lastname,
                               Date modified_date) throws UsernameExistsException;
}
