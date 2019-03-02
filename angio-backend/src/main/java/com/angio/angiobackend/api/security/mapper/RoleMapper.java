package com.angio.angiobackend.api.security.mapper;

import com.angio.angiobackend.api.security.dto.RoleDto;
import com.angio.angiobackend.api.security.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                PermissionMapper.class
        }
)
public interface RoleMapper {

    RoleDto toRoleDto(Role entity);

}
