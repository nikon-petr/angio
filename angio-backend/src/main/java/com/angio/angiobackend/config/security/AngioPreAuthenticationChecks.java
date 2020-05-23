package com.angio.angiobackend.config.security;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

@Slf4j
@AllArgsConstructor
public class AngioPreAuthenticationChecks implements UserDetailsChecker {

    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    public void check(UserDetails toCheck) {
        log.debug("check() - start");
        User user = (User) toCheck;

        log.debug("check() - check locked: {}", user.isAccountNonLocked());
        if (!user.isAccountNonLocked()) {
            throw new LockedException(msa.getMessage("errors.api.security.userLocked"));
        }

        log.debug("check() - check enabled: {}", user.isEnabled());
        if (!user.isEnabled()) {
            throw new DisabledException(msa.getMessage("errors.api.security.userDisabled"));
        }

        log.debug("check() - check account expiration: {}", user.isAccountNonExpired());
        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(msa.getMessage("errors.api.security.userAccountExpired"));
        }

        if (user.getOrganization() != null) {
            log.debug("check() - check organization locked: {}", user.getOrganization().isLocked());
            if (user.getOrganization() != null && user.getOrganization().isLocked()) {
                throw new OrganizationIsLockedException(msa.getMessage("errors.api.security.userOrganizationLocked"));
            }
        }

        log.debug("check() - end");
    }
}
