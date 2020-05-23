package com.angio.angiobackend.api.security.service;

import com.angio.angiobackend.api.security.dto.UpdateRoleDto;
import com.angio.angiobackend.api.security.dto.PermissionDto;
import com.angio.angiobackend.api.security.dto.RoleDto;

import java.util.List;

public interface RoleAndPermissionService {

    List<RoleDto> getAllRoles();

    RoleDto createRole(UpdateRoleDto dto);

    RoleDto updateRole(Long roleId, UpdateRoleDto dto);

    void deleteRole(Long id);

    List<PermissionDto> getAllPermissions();
}
