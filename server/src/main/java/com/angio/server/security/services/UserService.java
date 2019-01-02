package com.angio.server.security.services;

import com.angio.server.security.entities.UserEntity;
import com.angio.server.security.exception.IncorrectPasswordException;
import com.angio.server.security.exception.UsernameExistsException;
import com.angio.server.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

public interface UserService {
    UserEntity findByUsername(String username) throws UsernameNotFoundException;
    void registerNewUser(String username,
                               String password,
                               String firstname,
                               String lastname,
                               Date modified_date) throws UsernameExistsException;

    UserEntity changeUserName(String username, String newUsername) throws UsernameExistsException;
    UserEntity changePassword(String username, String password, String newPassword) throws IncorrectPasswordException, UsernameExistsException;

    Page<UserDto> findUsers(String query, Pageable pageable);

    UserEntity setUserEnabled(String username, boolean b) throws UsernameExistsException;
}
