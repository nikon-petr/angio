package com.angio.angiobackend.api.uploads.mapper;

import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import com.angio.angiobackend.api.uploads.entity.StaticFileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UploadMapper extends AbstractMapper<StaticFileEntity, StaticFileDto> {

    @Override
    @Mapping(target = "type", defaultValue = "1")
    StaticFileEntity toEntity(StaticFileDto dto);
}
