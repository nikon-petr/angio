package com.angio.angiobackend.api.user;

import com.angio.angiobackend.api.user.dto.UserBaseDto;
import com.angio.angiobackend.api.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "User", description = "User REST API")
@RequestMapping(path = "/api/v2/user")
public class UserResource {

    private final UserService userService;

    @ApiOperation("Create patient")
    @PatchMapping("/{id}")
    public UserBaseDto createPatient(@PathVariable UUID id, @RequestBody @Validated UserBaseDto dto) {
        return userService.updateUser(id, dto);
    }
}
