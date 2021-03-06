package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.DensityDto;
import com.angio.angiobackend.api.analyse.entity.Density;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper
public interface DensitiyMapper extends AbstractMapper<Density, DensityDto> {
}
