package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
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
                AdditionalInfoMapper.class,
                GeometricAnalyseMapper.class,
                BloodFlowAnalyseMapper.class
        })
public interface AnalyseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "geometricAnalyse", ignore = true)
    @Mapping(target = "bloodFlowAnalyse", ignore = true)
    @Mapping(target = "additionalInfo", qualifiedByName = "toNewAdditionalInfoEntity")
    AnalyseEntity toNewEntity(DetailedAnalyseDto dto);

    @Named("toAnalyseEntity")
    AnalyseEntity toEntity(DetailedAnalyseDto dto);

    @Mapping(target = "geometricAnalyse", qualifiedByName = "updateGeometricAnalyseEntity")
    @Mapping(target = "bloodFlowAnalyse", qualifiedByName = "updateBloodFlowAnalyseEntity")
    void updateAnalyseResult(AnalyseJmsDto dto, @MappingTarget AnalyseEntity entity);

    DetailedAnalyseDto toExtendedDto(AnalyseEntity entity);

    AnalyseJmsDto toAnalyseDto(AnalyseEntity entity);

    @Mapping(source = "additionalInfo.diagnostician.fullName", target = "diagnostician")
    AnalyseShortItemDto toShortItemDto(AnalyseEntity entity);
}
