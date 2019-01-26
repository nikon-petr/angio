package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.IschemiaDto;
import com.angio.angiobackend.api.analyse.entity.IshemiaEntity;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper
public interface IschemiaMapper extends AbstractMapper<IshemiaEntity, IschemiaDto> {
}
