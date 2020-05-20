package com.angio.angiobackend.api.security.service.impl;

import com.angio.angiobackend.api.security.dto.PermissionDto;
import com.angio.angiobackend.api.security.dto.RoleDto;
import com.angio.angiobackend.api.security.entity.Permission;
import com.angio.angiobackend.api.security.entity.Role;
import com.angio.angiobackend.api.security.mapper.PermissionMapper;
import com.angio.angiobackend.api.security.mapper.RoleMapper;
import com.angio.angiobackend.api.security.repository.PermissionRepository;
import com.angio.angiobackend.api.security.repository.RoleRepository;
import com.angio.angiobackend.api.security.service.RoleAndPermissionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoleAndPermissionServiceImpl implements RoleAndPermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        log.debug("getAllRoles() - start");
        log.debug("getAllRoles() - find roles");
        List<Role> roles = roleRepository.findAll();

        log.debug("getAllRoles() - map roles to dto");
        List<RoleDto> result = roleMapper.toRoleDto(roles);

        log.debug("getAllRoles() - end");
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getAllPermissions() {
        log.debug("getAllPermissions() - start");
        log.debug("getAllPermissions() - find permissions");
        List<Permission> roles = permissionRepository.findAll();

        log.debug("getAllPermissions() - map getAllPermissions to dto");
        List<PermissionDto> result = permissionMapper.toPermissionDto(roles);

        log.debug("getAllPermissions() - end");
        return result;
    }
}
