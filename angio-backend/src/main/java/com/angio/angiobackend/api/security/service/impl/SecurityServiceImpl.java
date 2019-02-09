package com.angio.angiobackend.api.security.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.security.JwtJdbcTokenStore;
import com.angio.angiobackend.api.security.entity.Token;
import com.angio.angiobackend.api.security.entity.User;
import com.angio.angiobackend.api.security.service.SecurityService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class SecurityServiceImpl implements SecurityService {

    private final JwtJdbcTokenStore tokenStore;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    @Transactional
    @PreAuthorize("hasRole('TOKEN_REVOKE')")
    public String revoke(@NonNull String tokenId) {

        log.debug("revoke() - start jti: {}", tokenId);
        Token token = tokenStore.findById(tokenId)
                .orElseThrow(() -> new IllegalArgumentException(
                        msa.getMessage("errors.api.token.tokenNotFound", new Object[] {tokenId})));
        User tokenIssuer = token.getUser();

        Authentication revocationIssuer = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.equals(tokenIssuer.getEmail(), revocationIssuer.getName())) {

            log.debug("revoke() - do revoke");
            tokenStore.removeRefreshTokenById(tokenId);
            return tokenId;
        }

        throw new UnauthorizedUserException(msa.getMessage("errors.api.token.revocationDenied", new Object[] {tokenId}));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('TOKEN_REMOVE')")
    public String removeToken(@NonNull String tokenId) {

        log.debug("remove() - start jti: {}", tokenId);

        log.debug("remove() - do remove");
        tokenStore.removeRefreshTokenById(tokenId);
        return tokenId;
    }
}
