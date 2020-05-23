package com.angio.angiobackend.api.security;

import com.angio.angiobackend.api.common.dto.Response;
import com.angio.angiobackend.api.security.dto.UpdateRoleDto;
import com.angio.angiobackend.api.security.dto.PermissionDto;
import com.angio.angiobackend.api.security.dto.RoleDto;
import com.angio.angiobackend.api.security.service.RoleAndPermissionService;
import com.angio.angiobackend.api.security.validation.group.NewRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ApiOperation("Create role")
    @PostMapping("/role")
    public RoleDto createRole(@RequestBody @Validated(NewRole.class) UpdateRoleDto dto) {
        return roleAndPermissionService.createRole(dto);
    }

    @ApiOperation("Update role")
    @PatchMapping("/role/{id}")
    public RoleDto updateRole(@PathVariable Long id, @RequestBody @Validated UpdateRoleDto dto) {
        return roleAndPermissionService.updateRole(id, dto);
    }

    @ApiOperation("Delete role")
    @DeleteMapping("/role/{id}")
    public Response deleteRole(@PathVariable Long id) {
        roleAndPermissionService.deleteRole(id);

        return Response.success(null);
    }

    @ApiOperation("Get all permissions")
    @GetMapping("/permission")
    public List<PermissionDto> getAllPermissions() {
        return roleAndPermissionService.getAllPermissions();
    }

}
