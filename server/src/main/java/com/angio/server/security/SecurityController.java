package com.angio.server.security;


import com.angio.server.AngioAppProperties;
import com.angio.server.security.entities.TokenEntity;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.util.jwt.JwtTokenUtil;
import com.angio.server.security.requests.TokenRequest;
import com.angio.server.security.services.TokenException;
import com.angio.server.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class SecurityController {

    private final AngioAppProperties angioAppProperties;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    @Autowired
    public SecurityController(
            AngioAppProperties angioAppProperties,
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            UserDetailsService userDetailsService,
            TokenService tokenService){
        this.angioAppProperties = angioAppProperties;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @RequestMapping(path = "/api/v1/auth/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody TokenRequest tokenRequest, Device device)
            throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        tokenRequest.getUsername(),
                        tokenRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        tokenService.putToken((UserEntity) userDetails, token);

        return ResponseEntity.noContent()
                .header("Authorization", "Bearer " + token)
                .build();
    }



    @RequestMapping(path = "/api/v1/auth/token/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(angioAppProperties.jwt.getHeader());
        token = jwtTokenUtil.getTokenBody(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            tokenService.putToken(user, refreshedToken);
            return ResponseEntity.noContent()
                    .header("Authorization", "Bearer " + refreshedToken)
                    .build();
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/auth/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = request.getHeader(angioAppProperties.jwt.getHeader());
            token = jwtTokenUtil.getTokenBody(token);
            TokenEntity tokenEntity = tokenService.findByToken(token);
            tokenService.revokeToken(tokenEntity.getId());
            return ResponseEntity.noContent().build();
        } catch (TokenException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/auth/revoke/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> revokeAuthenticationToken(@PathVariable("id") long id){
        try{
            tokenService.revokeToken(id);
            return ResponseEntity.noContent().build();
        } catch (TokenException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
