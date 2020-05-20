package com.angio.angiobackend.api.security.service;

import com.angio.angiobackend.api.security.dto.PermissionDto;
import com.angio.angiobackend.api.security.dto.RoleDto;

import java.util.List;

public interface RoleAndPermissionService {

    List<RoleDto> getAllRoles();

    List<PermissionDto> getAllPermissions();
}
