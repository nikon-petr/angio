package com.angio.server.user;

import com.angio.server.security.entities.UserEntity;
import com.angio.server.security.services.UserService;
import com.angio.server.user.requests.CreateUserRequest;
import com.angio.server.user.responses.SelfUserResponse;
import com.angio.server.user.responses.UserExistsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @RequestMapping(path = "/api/v1/user/self", method = RequestMethod.GET)
    public ResponseEntity<?> getSelfUser() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        final UserEntity userEntity = (UserEntity) userDetails;
        return ResponseEntity.ok()
                .body(new SelfUserResponse(
                        userEntity.getUsername(),
                        userEntity.getUserInfo().getFirstname(),
                        userEntity.getUserInfo().getLastname(),
                        userEntity.getAuthorities()
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
}