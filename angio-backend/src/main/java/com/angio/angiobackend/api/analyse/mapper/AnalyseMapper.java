package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.AnalyseDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.ExtendedAnalyseDto;
import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.user.mapstruct.UserInfoMapper;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {
                PatientMapper.class,
                UserInfoMapper.class,
                AnalyseStatusMapper.class,
                GeometricAnalyseMapper.class,
                BloodFlowAnalyseMapper.class
        })
public interface AnalyseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "geometricAnalyse", ignore = true)
    @Mapping(target = "bloodFlowAnalyse", ignore = true)
    @Mapping(target = "user.userInfo", ignore = true)
    @Mapping(target = "user.username", ignore = true)
    AnalyseEntity toNewEntity(ExtendedAnalyseDto dto);

    @Named("toAnalyseEntity")
    @Mapping(target = "user.userInfo", ignore = true)
    @Mapping(target = "user.username", ignore = true)
    AnalyseEntity toEntity(ExtendedAnalyseDto dto);

    @Mapping(source = "geometricAnalyse", target = "geometricAnalyse", qualifiedByName = "updateGeometricAnalyseEntity")
    @Mapping(source = "bloodFlowAnalyse", target = "bloodFlowAnalyse", qualifiedByName = "updateBloodFlowAnalyseEntity")
    void updateEntity(AnalyseDto dto, @MappingTarget AnalyseEntity entity);

    ExtendedAnalyseDto toExtendedDto(AnalyseEntity entity);

    AnalyseDto toAnalyseDto(AnalyseEntity entity);

    @Mapping(source = "user.userInfo.fullName", target = "diagnostician")
    AnalyseShortItemDto toShortItemDto(AnalyseEntity entity);
}
