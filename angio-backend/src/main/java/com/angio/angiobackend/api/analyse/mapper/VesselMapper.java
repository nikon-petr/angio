package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.VesselDto;
import com.angio.angiobackend.api.analyse.entity.VesselEntity;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.uploads.mapper.UploadMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {UploadMapper.class})
public interface VesselMapper extends AbstractMapper<VesselEntity, VesselDto> {
}
