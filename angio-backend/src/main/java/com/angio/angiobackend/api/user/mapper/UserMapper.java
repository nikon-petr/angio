package com.angio.angiobackend.api.user.mapper;

import com.angio.angiobackend.api.analyse.dto.DiagnosticianDto;
import com.angio.angiobackend.api.common.mapper.FullNameMapper;
import com.angio.angiobackend.api.organization.mapper.OrganizationMapper;
import com.angio.angiobackend.api.security.mapper.PermissionMapper;
import com.angio.angiobackend.api.security.mapper.RoleMapper;
import com.angio.angiobackend.api.user.dto.UpdateUserDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
import com.angio.angiobackend.api.user.dto.UserDetailsDto;
import com.angio.angiobackend.api.user.dto.UserDto;
import com.angio.angiobackend.api.user.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                FullNameMapper.class,
                RoleMapper.class,
                PermissionMapper.class,
                OrganizationMapper.class
        })
public interface UserMapper {

    DiagnosticianDto toDiagnostician(User entity);

    @InheritInverseConfiguration(name = "toDiagnostician")
    User toEntity(DiagnosticianDto dto);

    @Mapping(target = "organizationId", source = "organization.id")
    NewUserDto toNewUserDto(User entity);

    List<NewUserDto> toNewUserDtos(List<User> entities);

    @Mapping(target = "fullName", qualifiedByName = "mergeFullName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void updateEntity(UpdateUserDto dto, @MappingTarget User entity);

    UserDetailsDto toDetailedDto(User entity);

    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "permissions", qualifiedByName = "toStringPermission")
    UserDto toUserDto(User entity);
}
