package com.angio.angiobackend.api.security.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.OperationException;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.security.dto.PermissionDto;
import com.angio.angiobackend.api.security.dto.RoleDto;
import com.angio.angiobackend.api.security.dto.UpdateRoleDto;
import com.angio.angiobackend.api.security.entity.Permission;
import com.angio.angiobackend.api.security.entity.Role;
import com.angio.angiobackend.api.security.mapper.PermissionMapper;
import com.angio.angiobackend.api.security.mapper.RoleMapper;
import com.angio.angiobackend.api.security.repository.PermissionRepository;
import com.angio.angiobackend.api.security.repository.RoleRepository;
import com.angio.angiobackend.api.security.service.RoleAndPermissionService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoleAndPermissionServiceImpl implements RoleAndPermissionService {

    private final DynamicLocaleMessageSourceAccessor msa;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ROLE_VIEW')")
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
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public RoleDto createRole(@NotNull UpdateRoleDto dto) {
        log.debug("createRole() - start, dto={}", dto);
        log.debug("createRole() - find permissions");
        List<Permission> permissions = permissionRepository.findAllById(new HashSet<>(dto.getPermissionIds()));

        log.debug("createRole() - create new role entity");
        Role newRole = new Role()
                .setSystemRole(false)
                .setDescription(dto.getDescription())
                .setPermissions(new HashSet<>(permissions));

        log.debug("createRole() - save new role");
        newRole = roleRepository.save(newRole);

        log.debug("createRole() - map to dto and return");
        return roleMapper.toRoleDto(newRole);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_EDIT')")
    public RoleDto updateRole(@NonNull Long roleId, @NotNull UpdateRoleDto dto) {
        log.debug("updateRole() - start, id={}, dto={}", roleId, dto);
        log.debug("updateRole() - find role in database");
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.role.roleWithIdNotFound", new Object[]{roleId})));

        log.debug("updateRole() - check system role");
        if (role.isSystemRole()) {
            throw new OperationException(msa.getMessage("errors.api.role.updateImmutableRole"));
        }

        if (!CollectionUtils.isEmpty(dto.getPermissionIds())) {
            log.debug("updateRole() - find permissions");
            List<Permission> permissions = permissionRepository.findAllById(new HashSet<>(dto.getPermissionIds()));

            log.debug("updateRole() - set new permissions: {}", dto.getPermissionIds());
            role.setPermissions(new HashSet<>(permissions));
        }

        if (dto.getDescription() != null) {
            log.debug("updateRole() - set new description {}", dto.getDescription());
            role.setDescription(dto.getDescription());
        }

        log.debug("updateRole() - save updated role");
        role = roleRepository.save(role);

        log.debug("updateRole() - map to dto and return");
        return roleMapper.toRoleDto(role);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_REMOVE')")
    public void deleteRole(@NotNull Long id) {
        log.debug("deleteRole() - start, id={}", id);
        log.debug("deleteRole() - find role in database");
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.role.roleWithIdNotFound", new Object[]{id})));

        log.debug("deleteRole() - check system role");
        if (role.isSystemRole()) {
            throw new OperationException(msa.getMessage("errors.api.role.deleteImmutableRole"));
        }

        log.debug("deleteRole() - check role references");
        if (!CollectionUtils.isEmpty(role.getUsers()) || !CollectionUtils.isEmpty(role.getOwners())) {
            throw new OperationException(
                    msa.getMessage("errors.api.role.canNotDeleteRoleReferencedByUser", new Object[]{id}));
        }

        log.debug("deleteRole() - delete role");
        roleRepository.delete(role);

        log.debug("deleteRole() - end");
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
