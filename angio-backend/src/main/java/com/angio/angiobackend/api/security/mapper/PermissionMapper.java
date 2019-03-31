package com.angio.angiobackend.api.security.mapper;

import com.angio.angiobackend.api.security.dto.PermissionDto;
import com.angio.angiobackend.api.security.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    PermissionDto toPermissionDto(Permission entity);

    @Named("toStringPermission")
    default String toString(Permission entity) {
        return entity.getName();
    }
}
