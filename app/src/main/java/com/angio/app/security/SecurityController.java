package com.angio.app.security;


import com.angio.app.AngioAppProperties;
import com.angio.app.security.entities.TokenEntity;
import com.angio.app.security.entities.UserEntity;
import com.angio.app.security.jwt.JwtTokenUtil;
import com.angio.app.security.requests.AuthRequest;
import com.angio.app.security.requests.CheckUsernameRequest;
import com.angio.app.security.requests.RevokeTokenRequest;
import com.angio.app.security.responses.AuthenticationResponse;
import com.angio.app.security.responses.CheckUsernameResponse;
import com.angio.app.security.services.TokenException;
import com.angio.app.security.services.TokenService;
import com.angio.app.security.services.UserService;
import com.angio.app.userinfo.requests.RegisterRequest;
import com.angio.app.userinfo.services.UserInfoService;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping
public class SecurityController {

    private final AngioAppProperties angioAppProperties;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserInfoService userInfoService;
    private final TokenService tokenService;

    @Autowired
    public SecurityController(
            AngioAppProperties angioAppProperties,
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            UserDetailsService userDetailsService,
            UserService userService,
            UserInfoService userInfoService,
            TokenService tokenService){
        this.angioAppProperties = angioAppProperties;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.tokenService = tokenService;
    }

    @RequestMapping(path = "/api/v1/user/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterRequest registerRequest) {
        try {
            userService.registerNewUser(
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getFirstname(),
                    registerRequest.getLastname(),
                    new Date());

            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/user/register/check-username", method = RequestMethod.POST)
    public ResponseEntity<?> checkUsername(@RequestBody CheckUsernameRequest checkUsernameRequest){
        try{
            userService.findByUsername(checkUsernameRequest.getUsername());
            return ResponseEntity.ok(new CheckUsernameResponse("used"));
        } catch (UsernameNotFoundException e){
            return ResponseEntity.ok(new CheckUsernameResponse("unused"));
        }
    }

    @RequestMapping(path = "/api/v1/auth/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest, Device device)
            throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        tokenService.putToken((UserEntity) userDetails, token);

        return ResponseEntity.ok(new AuthenticationResponse(token, angioAppProperties.jwt.getTokenType()));
    }

    @RequestMapping(path = "/api/v1/auth/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(angioAppProperties.jwt.getHeader());
        token = jwtTokenUtil.getTokenBody(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            tokenService.putToken(user, refreshedToken);
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken, angioAppProperties.jwt.getTokenType()));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/auth/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = request.getHeader(angioAppProperties.jwt.getHeader());
            token = jwtTokenUtil.getTokenBody(token);
            TokenEntity tokenEntity = tokenService.findByToken(token);
            tokenService.revokeToken(tokenEntity.getId());
            return ResponseEntity.ok().body(null);
        } catch (TokenException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/auth/revoke", method = RequestMethod.POST)
    public ResponseEntity<?> revokeAuthenticationToken(@RequestBody RevokeTokenRequest revokeTokenRequest){
        try{
            tokenService.revokeToken(revokeTokenRequest.getId());
            return ResponseEntity.ok().body(null);
        } catch (TokenException e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }
}
