package com.angio.angiobackend.config;

import com.angio.angiobackend.api.security.AngioTokenEnhancer;
import com.angio.angiobackend.api.security.JwtJdbcTokenStore;
import com.angio.angiobackend.api.security.RefreshTokenFilter;
import com.angio.angiobackend.api.security.repository.TokenRepository;
import com.angio.angiobackend.api.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    public static final String CLIENT_ID = "angio-web-client";
    public static final String CLIENT_SECRET = "angio-client-secret";
    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String SCOPE_TRUST = "trust";
    public static final String SIGNING_KEY = "signing key";
    public static final Integer ACCESS_TOKEN_VALIDITY_SECONDS = 15 * 60;
    public static final Integer REFRESH_TOKEN_VALIDITY_SECONDS = 7 * 24 * 60 * 60;

    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final UserService userService;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer.inMemory()
                .withClient(CLIENT_ID)
                .secret("{noop}" + CLIENT_SECRET)
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)
                .scopes(SCOPE_TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

        endpoints.tokenStore(jwtJdbcTokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new AngioTokenEnhancer();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean
    public JwtJdbcTokenStore jwtJdbcTokenStore() {
        return new JwtJdbcTokenStore(accessTokenConverter(), tokenRepository, userService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .addTokenEndpointAuthenticationFilter(new RefreshTokenFilter(jwtJdbcTokenStore()));
    }
}
