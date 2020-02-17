package com.angio.angiobackend.api.user.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final DynamicLocaleMessageSourceAccessor msa;

    /**
     * Load user from database by username.
     *
     * @param uuid user email
     * @return user details for spring security
     * @throws UsernameNotFoundException throws when user id not found
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String uuid)
            throws UsernameNotFoundException {

        log.debug("loadUserByUsername() - start email: {}", uuid);
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UsernameNotFoundException(
                        msa.getMessage("errors.api.user.userWithIdNotFound", new Object[] {uuid})));

        log.debug("loadUserByUsername() - end");
        return user;
    }
}