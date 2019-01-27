package com.angio.angiobackend.api.security;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.security.entities.TokenEntity;
import com.angio.angiobackend.api.security.exception.TokenException;
import com.angio.angiobackend.util.JwtTokenUtils;
import com.angio.angiobackend.api.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AngioBackendProperties props;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(props.jwt.getHeader());
        authToken = jwtTokenUtils.getTokenBody(authToken);
        String username = jwtTokenUtils.getUsernameFromToken(authToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                userDetails = this.userDetailsService.loadUserByUsername(username);
            }
            catch (UsernameNotFoundException e) {
                userDetails = null;
            }
            
            if (userDetails != null && userDetails.isEnabled() && jwtTokenUtils.validateToken(authToken, userDetails)) {
                TokenEntity tokenEntity;
                try {
                    tokenEntity = tokenService.findById(jwtTokenUtils.getIdFromToken(authToken));
                } catch (TokenException e) {
                    tokenEntity = null;
                }
                if (tokenEntity != null && tokenEntity.isEnabled()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
