package com.angio.angiobackend.api.common.mapper;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import com.angio.angiobackend.api.common.embeddable.FullName;
import org.mapstruct.Mapper;

@Mapper
public interface FullNameMapper extends AbstractMapper<FullName, FullNameDto> {
}
