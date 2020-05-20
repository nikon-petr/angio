package com.angio.angiobackend.api.security.mapper;

import com.angio.angiobackend.api.security.dto.RoleDto;
import com.angio.angiobackend.api.security.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                PermissionMapper.class
        }
)
public interface RoleMapper {

    RoleDto toRoleDto(Role entity);

    List<RoleDto> toRoleDto(List<Role> roles);
}
