package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.RetinalPositiveExtremumDto;
import com.angio.angiobackend.api.analyse.entity.RetinalPositiveExtremum;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.uploads.mapper.UploadMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {
        UploadMapper.class
})
public interface RetinalPositiveExtremumMapper extends AbstractMapper<RetinalPositiveExtremum, RetinalPositiveExtremumDto> {
}
