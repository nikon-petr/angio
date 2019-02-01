package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.AdditionalInfoDto;
import com.angio.angiobackend.api.analyse.embeddable.AdditionalInfo;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.patient.mapper.PatientMapper;
import com.angio.angiobackend.api.user.mapstruct.UserInfoMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        uses = {
                PatientMapper.class,
                UserInfoMapper.class
        })
public interface AdditionalInfoMapper extends AbstractMapper<AdditionalInfo, AdditionalInfoDto> {

    @Override
    @Mapping(target = "diagnostician", ignore = true)
    AdditionalInfo toEntity(AdditionalInfoDto dto);

    @Mapping(target = "diagnostician", ignore = true)
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void updateEntity(AdditionalInfoDto dto, @MappingTarget AdditionalInfo entity);

    @Override
    @Mapping(source = "patient.id", target = "patientId")
    AdditionalInfoDto toDto(AdditionalInfo entity);
}
