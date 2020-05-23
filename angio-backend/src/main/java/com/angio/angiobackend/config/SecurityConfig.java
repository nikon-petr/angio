package com.angio.angiobackend.config;

import static com.angio.angiobackend.config.SwaggerConfig.SWAGGER_SERVICE_PATHS;
import static java.lang.String.format;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.user.service.UserService;
import com.angio.angiobackend.config.security.AngioAuthProvider;
import com.angio.angiobackend.config.security.AngioPreAuthenticationChecks;
import com.angio.angiobackend.init.Permissions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final AngioBackendProperties props;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        AngioAuthProvider provider = new AngioAuthProvider(userService);
        provider.setMsa(msa);
        provider.setPreAuthenticationChecks(new AngioPreAuthenticationChecks(msa));
        auth.authenticationProvider(provider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {

        web
                .ignoring()
                .antMatchers(SWAGGER_SERVICE_PATHS)
                .antMatchers(format("%s*", props.getUploadPath()))
                .antMatchers(
                        format("%s**/js/**", props.getFrontendDistPath()),
                        format("%s**/css/**", props.getFrontendDistPath()),
                        format("%s**/img/**", props.getFrontendDistPath()),
                        format("%s/favicon.ico", props.getFrontendDistPath()),
                        format("%s/index.html", props.getFrontendDistPath()))
                .regexMatchers("^((?!/api/|/oauth/token).)*$")
                .requestMatchers(EndpointRequest.to(InfoEndpoint.class))
                .antMatchers(HttpMethod.POST, "/api/v2/user/register")
                .antMatchers(HttpMethod.POST, "/api/v2/user/*/password/reset")
                .antMatchers(HttpMethod.POST, "/api/v2/user/{id}/reset")
                .antMatchers(HttpMethod.POST, "/api/v2/user/{id}/enable");
        }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .regexMatchers("^((?!/api/).)*$").permitAll()
                .requestMatchers(EndpointRequest.to(InfoEndpoint.class)).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority(Permissions.ACTUATOR_VIEW.name())
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
