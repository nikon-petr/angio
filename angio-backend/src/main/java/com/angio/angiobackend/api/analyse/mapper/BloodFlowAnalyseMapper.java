package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.BloodFlowAnalyseDto;
import com.angio.angiobackend.api.analyse.embeddable.BloodFlowAnalyse;
import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.uploads.mapper.UploadMapper;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {
                UploadMapper.class,
                IschemiaMapper.class,
                MaculaMapper.class,
                DensitiyMapper.class
        })
public interface BloodFlowAnalyseMapper extends AbstractMapper<BloodFlowAnalyse, BloodFlowAnalyseDto> {

        @Named("updateBloodFlowAnalyseEntity")
        void updateEntity(BloodFlowAnalyseDto dto, @MappingTarget BloodFlowAnalyse entity);
}
