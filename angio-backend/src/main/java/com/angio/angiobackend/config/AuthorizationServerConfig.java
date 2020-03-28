package com.angio.angiobackend.config;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.security.repository.TokenRepository;
import com.angio.angiobackend.api.user.service.UserService;
import com.angio.angiobackend.config.security.AngioTokenEnhancer;
import com.angio.angiobackend.config.security.JwtJdbcTokenStore;
import com.angio.angiobackend.config.security.RefreshTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String SCOPE_TRUST = "trust";

    private final AngioBackendProperties props;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final UserService userService;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer.inMemory()
                .withClient(props.getOauth().getClientId())
                .secret("{noop}" + props.getOauth().getClientSecret())
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)
                .scopes(SCOPE_TRUST)
                .accessTokenValiditySeconds((int) props.getOauth().getAccessTokenValidityDuration().getSeconds())
                .refreshTokenValiditySeconds((int) props.getOauth().getRefreshTokenValidityDuration().getSeconds());
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
        converter.setSigningKey(props.getOauth().getSigningKey());
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

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
