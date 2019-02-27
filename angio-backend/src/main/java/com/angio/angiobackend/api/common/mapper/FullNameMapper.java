package com.angio.angiobackend.api.common.mapper;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import com.angio.angiobackend.api.common.embeddable.FullName;
import com.angio.angiobackend.api.user.dto.UserBaseDto;
import com.angio.angiobackend.api.user.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface FullNameMapper extends AbstractMapper<FullName, FullNameDto> {

    @Named("mergeFullName")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void updateEntity(FullNameDto dto, @MappingTarget FullName entity);
}
