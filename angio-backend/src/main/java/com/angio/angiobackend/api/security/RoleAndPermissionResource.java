package com.angio.angiobackend.api.security;

import com.angio.angiobackend.api.security.dto.PermissionDto;
import com.angio.angiobackend.api.security.dto.RoleDto;
import com.angio.angiobackend.api.security.service.RoleAndPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Role And Permission", description = "Angio roles and permissions REST API")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v2")
public class RoleAndPermissionResource {

    private final RoleAndPermissionService roleAndPermissionService;

    @ApiOperation("Get all roles")
    @GetMapping("/role")
    public List<RoleDto> getAllRoles() {
        return roleAndPermissionService.getAllRoles();
    }

    @ApiOperation("Get all permissions")
    @GetMapping("/permission")
    public List<PermissionDto> getAllPermissions() {
        return roleAndPermissionService.getAllPermissions();
    }

}
