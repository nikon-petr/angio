package com.angio.angiobackend.api.user;

import com.angio.angiobackend.api.security.dto.UserTokenDto;
import com.angio.angiobackend.api.security.entities.TokenEntity;
import com.angio.angiobackend.api.security.entities.UserEntity;
import com.angio.angiobackend.api.security.exception.IncorrectPasswordException;
import com.angio.angiobackend.api.security.services.TokenService;
import com.angio.angiobackend.api.security.services.UserService;
import com.angio.angiobackend.api.security.exception.UsernameExistsException;
import com.angio.angiobackend.api.user.dto.UserDto;
import com.angio.angiobackend.api.user.requests.ChangePasswordRequest;
import com.angio.angiobackend.api.user.requests.ChangeUsernameRequest;
import com.angio.angiobackend.api.user.requests.CreateUserRequest;
import com.angio.angiobackend.api.user.responses.SelfUserResponse;
import com.angio.angiobackend.api.user.responses.UserExistsResponse;
import com.angio.angiobackend.util.JwtTokenUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(
            UserService userService,
            UserDetailsService userDetailsService,
            TokenService tokenService,
            JwtTokenUtil jwtTokenUtil,
            ModelMapper modelMapper) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(path = "/api/v1/user", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            userService.registerNewUser(
                    createUserRequest.getUsername(),
                    createUserRequest.getPassword(),
                    createUserRequest.getFirstName(),
                    createUserRequest.getLastName(),
                    new Date());

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/user", method = RequestMethod.GET)
    public Page<UserDto> getUserList(@RequestParam(value = "query", required = false) String query, Pageable pageable) {
        return userService.findUsers(query, pageable);
    }

    @RequestMapping(path = "/api/v1/user/change-email", method = RequestMethod.POST)
    public ResponseEntity<?> changeUsername(@RequestBody ChangeUsernameRequest changeUsernameRequest, HttpServletRequest request)
            throws UsernameExistsException {
        final UserDetails userEntity = (UserEntity) ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());

        UserEntity updatedUser = userService.changeUserName(userEntity.getUsername(), changeUsernameRequest.getNewEmail());

        String token = request.getHeader("Authorization");
        token = jwtTokenUtil.getTokenBody(token);
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(updatedUser.getUsername());

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String device = userAgent.getOperatingSystem().getDeviceType().getName();
        String os = userAgent.getOperatingSystem().getName();
        String browserVersionString = userAgent.getBrowserVersion() != null ? " " + userAgent.getBrowserVersion().getMajorVersion() : "";
        String browser = userAgent.getBrowser().getName() + browserVersionString;

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            TokenEntity tokenEntity = tokenService.putToken(user, os, browser, device);
            String refreshedToken = jwtTokenUtil.refreshToken(token, tokenEntity.getId(), user.getUsername());
            tokenService.putTokenExpirationAndIssuedAt(tokenEntity, jwtTokenUtil.getExpirationDateFromToken(token), jwtTokenUtil.getIssuedAtDateFromToken(token));
            tokenService.revokeTokenAsAdmin(jwtTokenUtil.getIdFromToken(token));
            return ResponseEntity.noContent()
                    .header("Authorization", "Bearer "+ refreshedToken)
                    .build();
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/api/v1/user/change-password", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @RequestHeader("User-Agent") String userAgentHeader)
            throws UsernameExistsException, IncorrectPasswordException {
        final UserDetails userEntity = (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        userService.changePassword(
                userEntity.getUsername(),
                changePasswordRequest.getPassword(),
                changePasswordRequest.getNewPassword());

        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentHeader);
        String device = userAgent.getOperatingSystem().getDeviceType().getName();
        String os = userAgent.getOperatingSystem().getName();
        String browserVersionString = userAgent.getBrowserVersion() != null ? " " + userAgent.getBrowserVersion().getMajorVersion() : "";
        String browser = userAgent.getBrowser().getName() + browserVersionString;

        TokenEntity tokenEntity = tokenService.putToken((UserEntity) userEntity, os, browser, device);
        final String token = jwtTokenUtil.generateToken(userEntity, tokenEntity.getId(), device);
        tokenService.putTokenExpirationAndIssuedAt(tokenEntity, jwtTokenUtil.getExpirationDateFromToken(token), jwtTokenUtil.getIssuedAtDateFromToken(token));

        return ResponseEntity.noContent()
                .header("Authorization", "Bearer " + token)
                .build();
    }

    @RequestMapping(path = "/api/v1/user/self", method = RequestMethod.GET)
    public ResponseEntity<?> getSelfUser(@RequestHeader("Authorization") String authorizationHeader) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String token = jwtTokenUtil.getTokenBody(authorizationHeader);
        final UserEntity userEntity = (UserEntity) userDetails;
        return ResponseEntity.ok()
                .body(new SelfUserResponse(
                        userEntity.getUsername(),
                        userEntity.getUserInfo().getFullName().getFirstname(),
                        userEntity.getUserInfo().getFullName().getLastname(),
                        userEntity.getAuthorities(),
                        jwtTokenUtil.getIdFromToken(token)
                ));
    }

    @RequestMapping(path = "/api/v1/user/username-exists/{username:.+}", method = RequestMethod.GET)
    public ResponseEntity<?> checkUsername(@PathVariable("username") String username) {
        try {
            userService.findByUsername(username);
            return ResponseEntity.ok(new UserExistsResponse(true));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.ok(new UserExistsResponse(false));
        }
    }

    @RequestMapping(path = "/api/v1/user/sessions", method = RequestMethod.GET)
    public List<UserTokenDto> getEnabledTokensForUser(Principal principal) {
        return tokenService.findByUsername(principal.getName())
                .stream()
                .map((tokenEntity) -> modelMapper.map(tokenEntity, UserTokenDto.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/api/v1/user/{username:.+}/sessions", method = RequestMethod.GET)
    public List<UserTokenDto> getTokensForUser(@PathVariable("username") String username) {
        return tokenService.findByUsername(username)
                .stream()
                .map((tokenEntity) -> modelMapper.map(tokenEntity, UserTokenDto.class))
                .collect(Collectors.toList());
    }
}