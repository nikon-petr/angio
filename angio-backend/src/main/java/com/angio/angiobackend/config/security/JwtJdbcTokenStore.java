package com.angio.angiobackend.config.security;

import com.angio.angiobackend.api.security.entity.Token;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.security.repository.TokenRepository;
import com.angio.angiobackend.api.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class JwtJdbcTokenStore extends JwtTokenStore {

    private final JsonParser parser = JsonParserFactory.getJsonParser();
    private final TokenRepository tokenRepository;
    private final UserService userService;

    public JwtJdbcTokenStore(JwtAccessTokenConverter jwtTokenEnhancer, TokenRepository tokenRepository,
                             UserService userService) {
        super(jwtTokenEnhancer);
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        log.debug("storeRefreshToken() - start");
        Map<String, ?> tokenData = getJwtClaims(refreshToken.getValue());

        log.debug("storeRefreshToken() - find user email={} in db", authentication.getName());
        User user = userService.findUserEntityByUuid(authentication.getName());

        Token token = new Token()
                .setUser(user)
                .setId(UUID.fromString((String) tokenData.get("jti")))
                .setIssuedAt(new Date())
                .setExpiresIn(new Date(((Integer) tokenData.get("exp")) * 1000L));
        log.debug("storeRefreshToken() - save token: {}", token);

        log.debug("storeRefreshToken() - send");
        tokenRepository.save(token);
    }

    public boolean inWhiteList(String refreshToken) {
        log.debug("inWhiteList() - start token: {}", refreshToken);
        Map<String, ?> tokenData = getJwtClaims(refreshToken);
        log.debug("inWhiteList() - end");
        return tokenRepository.existsById(UUID.fromString((String) tokenData.get("jti")));
    }

    private Map<String, ?> getJwtClaims(String tokenValue) {
        return parser.parseMap(JwtHelper.decode(tokenValue).getClaims());
    }

    public void removeRefreshTokenById(String tokenId) {
        log.debug("removeRefreshTokenById() - start jti: {}", tokenId);
        log.debug("removeRefreshTokenById() - end");
        tokenRepository.deleteById(UUID.fromString(tokenId));
    }

    public Optional<Token> findById(String tokenId) {
        log.debug("findById() - start jti: {}", tokenId);
        log.debug("findById() - end");
        return tokenRepository.findById(UUID.fromString(tokenId));
    }
}
