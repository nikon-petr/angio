package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.CysticVolumeDto;
import com.angio.angiobackend.api.analyse.entity.CysticVolume;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.uploads.mapper.UploadMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {
        UploadMapper.class,
})
public interface CysticVolumeMapper extends AbstractMapper<CysticVolume, CysticVolumeDto> {
}
