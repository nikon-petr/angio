package com.angio.server.security;


import com.angio.server.AngioAppProperties;
import com.angio.server.security.entities.TokenEntity;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.security.requests.TokenRequest;
import com.angio.server.security.exception.TokenException;
import com.angio.server.security.services.TokenService;
import com.angio.server.security.services.UserService;
import com.angio.server.security.exception.UsernameExistsException;
import com.angio.server.util.JwtTokenUtil;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping
@Api(description = "Angio security resource (version 1)")
public class SecurityController {

    private final AngioAppProperties angioAppProperties;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public SecurityController(
            AngioAppProperties angioAppProperties,
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            UserDetailsService userDetailsService,
            UserService userService, TokenService tokenService) {
        this.angioAppProperties = angioAppProperties;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @RequestMapping(path = "/api/v1/auth/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody TokenRequest tokenRequest, @RequestHeader("User-Agent") String userAgentHeader)
            throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        tokenRequest.getUsername(),
                        tokenRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentHeader);
        String device = userAgent.getOperatingSystem().getDeviceType().getName();
        String os = userAgent.getOperatingSystem().getName();
        String browserVersionString = userAgent.getBrowserVersion() != null ? " " + userAgent.getBrowserVersion().getMajorVersion() : "";
        String browser = userAgent.getBrowser().getName() + browserVersionString;

        final UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRequest.getUsername());
        TokenEntity tokenEntity = tokenService.putToken((UserEntity) userDetails, os, browser, device);
        final String token = jwtTokenUtil.generateToken(userDetails, tokenEntity.getId(), device);
        tokenService.putTokenExpirationAndIssuedAt(tokenEntity, jwtTokenUtil.getExpirationDateFromToken(token), jwtTokenUtil.getIssuedAtDateFromToken(token));

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

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String device = userAgent.getOperatingSystem().getDeviceType().getName();
        String os = userAgent.getOperatingSystem().getName();
        String browserVersionString = userAgent.getBrowserVersion() != null ? " " + userAgent.getBrowserVersion().getMajorVersion() : "";
        String browser = userAgent.getBrowser().getName() + browserVersionString;

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            TokenEntity tokenEntity = tokenService.putToken(user, os, browser, device);
            String refreshedToken = jwtTokenUtil.refreshToken(token, tokenEntity.getId());
            tokenService.putTokenExpirationAndIssuedAt(tokenEntity, jwtTokenUtil.getExpirationDateFromToken(token), jwtTokenUtil.getIssuedAtDateFromToken(token));
            tokenService.revokeTokenAsAdmin(jwtTokenUtil.getIdFromToken(token));
            return ResponseEntity.noContent()
                    .header("Authorization", "Bearer "+ refreshedToken)
                    .build();
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/auth/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = jwtTokenUtil.getTokenBody(authorizationHeader);
            TokenEntity tokenEntity = tokenService.findById(jwtTokenUtil.getIdFromToken(token));
            tokenService.revokeTokenAsAdmin(tokenEntity.getId());
            return ResponseEntity.noContent().build();
        } catch (TokenException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/auth/revoke/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> revokeAuthenticationToken(@PathVariable("id") long id) {
        try {
            tokenService.revokeTokenAsAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (TokenException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/user/{username:.+}/disable", method = RequestMethod.POST)
    public ResponseEntity<?> disableUser(@PathVariable("username") String username) {
        try {
            userService.setUserEnabled(username, false);
            return ResponseEntity.noContent().build();
        } catch (UsernameExistsException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/user/{username:.+}/enable", method = RequestMethod.POST)
    public ResponseEntity<?> enableUser(@PathVariable("username") String username) {
        try {
            userService.setUserEnabled(username, true);
            return ResponseEntity.noContent().build();
        } catch (UsernameExistsException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/auth/close-session/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> revokeAuthenticationTokenAsUser(@PathVariable("id") long id, Principal principal) {
        try {
            tokenService.revokeTokenAsUser(id, principal.getName());
            return ResponseEntity.noContent().build();
        } catch (TokenException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
