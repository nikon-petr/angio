package com.angio.angiobackend.api.user;

import com.angio.angiobackend.api.common.dto.Response;
import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.EnableUserDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.ResetUserDto;
import com.angio.angiobackend.api.user.dto.SettingsDto;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserDto;
import com.angio.angiobackend.api.user.dto.UserLockedDto;
import com.angio.angiobackend.api.user.service.UserService;
import com.angio.angiobackend.api.user.validation.group.EnableUser;
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
    public List<NewUserDto> createPatient(@RequestBody @Validated List<NewUserDto> dtos) {
        return userService.createUsers(dtos);
    }

    @ApiOperation("Get user by id")
    @GetMapping("/{id}")
    public UserDetailsDto getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @ApiOperation("Get current user")
    @GetMapping("/me")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @ApiOperation("Update current user")
    @PatchMapping("/me")
    public UserDetailsDto updateCurrentUser(@RequestBody @Validated UpdateUserDto dto) {
        return userService.updateUser(dto);
    }

    @ApiOperation("Get current user settings")
    @GetMapping("/me/settings")
    public SettingsDto getCurrentUserSettings() {
        return userService.getSettings();
    }

    @ApiOperation("Update current user settings")
    @PatchMapping("/me/settings")
    public SettingsDto changeCurrentUserSettings(@RequestBody SettingsDto dto) {
        return userService.updateSettings(dto);
    }

    @ApiOperation("Reset current user settings to default")
    @PostMapping("/me/settings/reset")
    public SettingsDto resetCurrentUserSettingsToDefault() {
        return userService.resetSettingsToDefault();
    }

    @ApiOperation("Change current user password")
    @PostMapping("/me/password")
    public UserDetailsDto changeCurrentUserPassword(@RequestBody @Validated ChangePasswordDto dto) {
        return userService.changePassword(dto);
    }

    @ApiOperation("Reset current user password")
    @PostMapping("/{email:.+}/password/reset")
    public Response resetCurrentUserPassword(@PathVariable String email) {
        userService.resetPassword(email);
        return Response.success(null);
    }

    @ApiOperation("Reset user")
    @PostMapping("/{id}/reset")
    public Response resetUserPassword(@PathVariable UUID id, @RequestBody @Validated ResetUserDto resetUser) {
        userService.resetUser(id, resetUser);
        return Response.success(null);
    }

    @ApiOperation("Enable user")
    @PostMapping("{id}/enable")
    public UserDetailsDto enableUser(@PathVariable UUID id,
                                     @RequestBody @Validated(EnableUser.class) EnableUserDto enableUser) {
        return userService.enableUser(id, enableUser);
    }

    @ApiOperation("Change locked property")
    @PostMapping("/{id}/locked")
    public UserDetailsDto changeUserLocked(@PathVariable UUID id, @RequestBody @Validated UserLockedDto dto) {
        return userService.changeUserLocked(id, dto);
    }
}
