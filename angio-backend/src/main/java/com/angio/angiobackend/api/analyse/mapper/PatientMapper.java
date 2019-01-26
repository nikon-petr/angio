package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.PatientDto;
import com.angio.angiobackend.api.analyse.entity.PatientEntity;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.common.mapper.FullNameMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = {FullNameMapper.class})
public interface PatientMapper extends AbstractMapper<PatientEntity, PatientDto> {

    void toEntity(PatientDto dto, @MappingTarget PatientEntity entity);
}
