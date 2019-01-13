package com.angio.angiobackend.security;

import com.angio.angiobackend.AngioBackendProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

import static com.angio.angiobackend.SwaggerConfig.SWAGGER_SERVICE_PATHS;
import static java.lang.String.format;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AngioBackendProperties props;

    @Resource(name="userService")
    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, AngioBackendProperties props) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.props = props;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()

                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()

                .antMatchers(SWAGGER_SERVICE_PATHS).permitAll()
                .antMatchers(format("/%s*", props.getUploadPath())).permitAll()

                .antMatchers(HttpMethod.POST, "/api/v1/auth/token").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/user/username-exists/*").permitAll()
                .antMatchers("/api/v1/analyse").permitAll()
                .antMatchers("/api/v1/analyse/info").permitAll()
                .antMatchers("/api/v1/analyse/policy-exists").permitAll()
                .antMatchers("/api/v1/analyse/detail").permitAll()
                .antMatchers("/api/v1/analyse/detail/conclusion").permitAll()
                .antMatchers("/api/v1/analyse/vessel").permitAll()
                .antMatchers("/api/v1/image").permitAll()

                .antMatchers("/api/v2/analyse").permitAll()

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/user/self").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/v1/user/sessions").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/user/change-email").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/user/change-password").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/auth/refresh").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v1/auth/logout").hasRole("USER")
//                .antMatchers("images/analyses/*").hasRole("USER")

                .antMatchers(HttpMethod.POST, "/api/v1/auth/revoke/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/user/*/sessions").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/user/*/enable").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/user/*/disable").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()

                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl().disable();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.addExposedHeader("Content-Type");
        configuration.addExposedHeader("Authorization");
        configuration.setMaxAge(3600L);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }
}
