package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.ExecutionConfigurationDto;
import com.angio.angiobackend.api.analyse.embeddable.ExecutionConfiguration;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper
public interface ExecutionConfigurationMapper
        extends AbstractMapper<ExecutionConfiguration, ExecutionConfigurationDto> {
}
