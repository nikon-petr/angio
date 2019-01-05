package com.angio.server.security.services.impl;

import com.angio.server.common.embeddable.FullName;
import com.angio.server.security.exception.IncorrectPasswordException;
import com.angio.server.security.services.UserService;
import com.angio.server.security.exception.UsernameExistsException;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.repositories.AnalyseInfoCrudRepository;
import com.angio.server.security.entities.AuthorityEntity;
import com.angio.server.security.entities.TokenEntity;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.security.repositories.AuthorityCrudRepository;
import com.angio.server.security.repositories.TokenCrudRepository;
import com.angio.server.security.repositories.UserRepository;
import com.angio.server.user.dto.UserDto;
import com.angio.server.user.entities.UserInfoEntity;
import com.angio.server.user.repositories.UserInfoCrudRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserInfoCrudRepository userInfoCrudRepository;
    private final AuthorityCrudRepository authorityCrudRepository;
    private final TokenCrudRepository tokenCrudRepository;
    private final AnalyseInfoCrudRepository analyseInfoCrudRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserInfoCrudRepository userInfoCrudRepository,
            AuthorityCrudRepository authorityCrudRepository,
            TokenCrudRepository tokenCrudRepository,
            AnalyseInfoCrudRepository analyseInfoCrudRepository,
            PasswordEncoder passwordEncoder,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userInfoCrudRepository = userInfoCrudRepository;
        this.authorityCrudRepository = authorityCrudRepository;
        this.tokenCrudRepository = tokenCrudRepository;
        this.analyseInfoCrudRepository = analyseInfoCrudRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
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
        if(userRepository.existsById(username)){
            throw new UsernameExistsException("There is user with the same username");
        }

        UserEntity userEntity = new UserEntity(username, passwordEncoder.encode(password), true, new Date());
        userEntity = userRepository.save(userEntity);

        authorityCrudRepository.save(new AuthorityEntity(userEntity, "ROLE_USER"));

        UserInfoEntity userInfoEntity = new UserInfoEntity(
                userRepository.findByUsername(username).get(0),
                new FullName(firstname, lastname, ""),
                modified_date);
        userInfoCrudRepository.save(userInfoEntity);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserEntity changeUserName(String username, String newUsername) throws UsernameExistsException {
        if(userRepository.existsById(newUsername)){
            throw new UsernameExistsException("There is user with the same username");
        }

        UserEntity userEntity = userRepository.findById(username).get();
        UserEntity renamedUserEntity = userRepository.save(
                new UserEntity(
                        newUsername,
                        userEntity.getPassword(),
                        userEntity.isEnabled(),
                        userEntity.getLastPasswordResetDate()));
        Set<AuthorityEntity> authorityEntities = userEntity.getAuthorities();
        UserInfoEntity userInfoEntity = userEntity.getUserInfo();
        Set<TokenEntity> tokenEntities = userEntity.getTokens();
        Set<AnalyseInfoEntity> analyseInfoEntities = userEntity.getAnalysesInfo();
        authorityEntities.forEach((e) -> e.setUsername(renamedUserEntity));
        userInfoEntity.setUser(renamedUserEntity);
        tokenEntities.forEach((e) -> e.setUser(renamedUserEntity));
        analyseInfoEntities.forEach((e) -> e.setUser(renamedUserEntity));
        authorityCrudRepository.saveAll(authorityEntities);
        userInfoCrudRepository.save(userInfoEntity);
        tokenCrudRepository.saveAll(tokenEntities);
        analyseInfoCrudRepository.saveAll(analyseInfoEntities);
        userRepository.delete(userEntity);

        return renamedUserEntity;
    }

    @Override
    public UserEntity changePassword(String username, String password, String newPassword)
            throws IncorrectPasswordException, UsernameExistsException {
        UserEntity userEntity = userRepository.findById(username).get();

        if (userEntity == null){
            throw new UsernameExistsException("User does not exists");
        }

        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new IncorrectPasswordException("Incorrect password");
        }

        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userEntity.setLastPasswordResetDate(new Date());
        userEntity = userRepository.save(userEntity);
        return  userEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> findUsers(String query, Pageable pageable) {
        Page<UserEntity> users;
        if (query == null) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findByQuery(query, pageable);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return new PageImpl<>(
                users.getContent().stream()
                        .map(user -> modelMapper.map(user, UserDto.class))
                        .collect(Collectors.toList()),
                pageable,
                users.getTotalElements());
    }

    @Override
    @Transactional
    public UserEntity setUserEnabled(String username, boolean b) throws UsernameExistsException {
        UserEntity user = userRepository.findById(username).get();

        if (user == null) {
            throw new UsernameExistsException("Username does not exists");
        }

        user.setEnabled(b);
        return userRepository.save(user);
    }
}
