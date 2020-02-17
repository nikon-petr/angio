package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.MaculaDto;
import com.angio.angiobackend.api.analyse.embeddable.Macula;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper
public interface MaculaMapper extends AbstractMapper<Macula, MaculaDto> {
}
