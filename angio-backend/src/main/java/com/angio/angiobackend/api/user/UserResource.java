package com.angio.angiobackend.api.user;

import com.angio.angiobackend.api.common.dto.Response;
import com.angio.angiobackend.api.user.dto.ChangePasswordDto;
import com.angio.angiobackend.api.user.dto.EnableUserDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.RegisterUserDto;
import com.angio.angiobackend.api.user.dto.ResetUserDto;
import com.angio.angiobackend.api.user.dto.SettingsDto;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserDto;
import com.angio.angiobackend.api.user.dto.UserLockedDto;
import com.angio.angiobackend.api.user.service.UserService;
import com.angio.angiobackend.api.user.validation.group.EnableUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
    public List<UserDetailsDto> createUsers(@RequestBody @Validated List<NewUserDto> dtos) {
        return userService.createUsers(dtos);
    }

    @ApiOperation(value = "Filter users by query string")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "format: property,asc|desc")})
    @GetMapping
    public Page<UserDetailsDto> filterUsers(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            @RequestParam(value = "locked", required = false) Boolean locked,
            @RequestParam(value = "organizationId", required = false) Long organizationId,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            @RequestParam(value = "ownedRoleIds", required = false) List<Long> ownedRoleIds,
            @ApiIgnore @PageableDefault Pageable pageable) {
        return userService.filterUsersByQueryString(search, enabled, locked, organizationId, roleIds, ownedRoleIds, pageable);
    }

    @ApiOperation("Register user")
    @PostMapping("/register")
    public Response registerUser(@RequestBody @Validated RegisterUserDto dto) {
        return userService.registerUser(dto);
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

    @ApiOperation("Change user role list")
    @PostMapping("/{id}/role")
    public UserDetailsDto changeUserRoles(@PathVariable UUID id, @RequestBody List<Long> roleIds) {
        return userService.changeUserRoles(id, roleIds);
    }

    @ApiOperation("Change user owned role list")
    @PostMapping("/{id}/owned-role")
    public UserDetailsDto changeUserOwnedRoles(@PathVariable UUID id, @RequestBody List<Long> roleIds) {
        return userService.changeUserOwnedRoles(id, roleIds);
    }
}
