package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseReportDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import com.angio.angiobackend.api.analyse.entity.Analyse;
import com.angio.angiobackend.api.patient.mapper.PatientMapper;
import com.angio.angiobackend.api.uploads.mapper.UploadMapper;
import com.angio.angiobackend.api.user.mapper.UserMapper;
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
                UploadMapper.class,
                UserMapper.class,
                AnalyseStatusMapper.class,
                ExecutionConfigurationMapper.class,
                AdditionalInfoMapper.class,
                GeometricAnalyseMapper.class,
                BloodFlowAnalyseMapper.class,
                ProfileAnalyseMapper.class
        })
public interface AnalyseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "geometricAnalyse", ignore = true)
    @Mapping(target = "bloodFlowAnalyse", ignore = true)
    @Mapping(target = "profileAnalyse", ignore = true)
    @Mapping(target = "additionalInfo", qualifiedByName = "toNewAdditionalInfoEntity")
    Analyse toNewEntity(DetailedAnalyseDto dto);

    @Named("toAnalyseEntity")
    Analyse toEntity(DetailedAnalyseDto dto);

    @Mapping(target = "geometricAnalyse", qualifiedByName = "updateGeometricAnalyseEntity")
    @Mapping(target = "bloodFlowAnalyse", qualifiedByName = "updateBloodFlowAnalyseEntity")
    @Mapping(target = "profileAnalyse", qualifiedByName = "updateProfileAnalyseEntity")
    void updateAnalyseResult(AnalyseJmsDto dto, @MappingTarget Analyse entity);

    DetailedAnalyseDto toExtendedDto(Analyse entity);

    @Mapping(source = "additionalInfo.patient", target = "patient")
    AnalyseReportDto toReportDto(Analyse entity);

    AnalyseJmsDto toAnalyseDto(Analyse entity);

    @Mapping(source = "additionalInfo.diagnostician.fullName", target = "diagnostician")
    @Mapping(source = "additionalInfo.name", target = "name")
    @Mapping(source = "additionalInfo.shortDescription", target = "shortDescription")
    @Mapping(source = "additionalInfo.type", target = "analyseType")
    @Mapping(source = "additionalInfo.patient", target = "patient")
    AnalyseShortItemDto toShortItemDto(Analyse entity);
}
