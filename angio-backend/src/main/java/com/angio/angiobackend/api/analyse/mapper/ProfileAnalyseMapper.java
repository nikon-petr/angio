package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.ProfileAnalyseDto;
import com.angio.angiobackend.api.analyse.embeddable.ProfileAnalyse;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(uses = {
        CysticVolumeMapper.class,
        RetinalPositiveExtremumMapper.class
})
public interface ProfileAnalyseMapper extends AbstractMapper<ProfileAnalyse, ProfileAnalyseDto> {

    @Named("updateProfileAnalyseEntity")
    void updateEntity(ProfileAnalyseDto dto, @MappingTarget ProfileAnalyse entity);
}
