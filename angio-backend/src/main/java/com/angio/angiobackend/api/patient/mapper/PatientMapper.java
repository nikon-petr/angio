package com.angio.angiobackend.api.patient.mapper;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.entity.Patient;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.common.mapper.FullNameMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {FullNameMapper.class})
public interface PatientMapper extends AbstractMapper<Patient, PatientDto> {

    @BeanMapping(
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    void toEntity(PatientDto dto, @MappingTarget Patient entity);
}
