package com.angio.angiobackend.api.user.mapper;

import com.angio.angiobackend.api.analyse.dto.DiagnosticianDto;
import com.angio.angiobackend.api.common.mapper.FullNameMapper;
import com.angio.angiobackend.api.user.dto.UserBaseDto;
import com.angio.angiobackend.api.user.dto.NewUserDto;
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
                FullNameMapper.class
        })
public interface UserMapper {

    DiagnosticianDto toDiagnostician(User entity);

    UserBaseDto toBaseDto(User user);

    @InheritInverseConfiguration(name = "toDiagnostician")
    User toEntity(DiagnosticianDto dto);

    NewUserDto toNewUserDto(User entity);

    List<NewUserDto> toNewUserDtos(List<User> entities);

    @Mapping(target = "fullName", qualifiedByName = "mergeFullName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void updateEntity(UserBaseDto dto, @MappingTarget User entity);
}
