package com.angio.angiobackend.api.user;

import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserLockedDto;
import com.angio.angiobackend.api.user.service.UserService;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "User", description = "User REST API")
@RequestMapping(path = "/api/v2/user")
public class UserResource {

    private final UserService userService;

    @ApiOperation("Create users")
    @PostMapping
    public List<NewUserDto> createPatient(@RequestBody @Validated List<NewUserDto> dtos)
            throws IOException, TemplateException {
        return userService.createUsers(dtos);
    }

    @ApiOperation("Get user by id")
    @GetMapping("/{id}")
    public UserDetailsDto getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @ApiOperation("Update user")
    @PatchMapping("/{id}")
    public UserDetailsDto updateUser(@PathVariable UUID id, @RequestBody @Validated UpdateUserDto dto) {
        return userService.updateUser(id, dto);
    }

    @ApiOperation("Change password")
    @PostMapping("/{id}/password")
    public UserDetailsDto changePassword(@PathVariable UUID id, @RequestBody @Validated ChangePasswordDto dto) {
        return userService.changePassword(id, dto);
    }

    @ApiOperation("Change locked property")
    @PostMapping("/{id}/locked")
    public UserDetailsDto changeUserLocked(@PathVariable UUID id, @RequestBody @Validated UserLockedDto dto) {
        return userService.changeUserLocked(id, dto);
    }
}
