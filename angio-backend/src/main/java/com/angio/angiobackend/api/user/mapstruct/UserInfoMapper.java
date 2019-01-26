package com.angio.angiobackend.api.user.mapstruct;

import com.angio.angiobackend.api.analyse.dto.DiagnosticianDto;
import com.angio.angiobackend.api.common.mapper.FullNameMapper;
import com.angio.angiobackend.api.security.entities.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                FullNameMapper.class
        })
public interface UserInfoMapper {

    @Mapping(source = "userInfo.fullName", target = "fullName")
    DiagnosticianDto toDiagnostician(UserEntity entity);

    @InheritInverseConfiguration(name = "toDiagnostician")
    UserEntity toEntity(DiagnosticianDto dto);
}
