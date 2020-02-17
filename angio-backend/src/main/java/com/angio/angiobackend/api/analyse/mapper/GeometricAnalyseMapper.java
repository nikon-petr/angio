package com.angio.angiobackend.api.analyse.mapper;

import com.angio.angiobackend.api.analyse.dto.GeometricAnalyseDto;
import com.angio.angiobackend.api.analyse.dto.GeometricAnalyseReportDto;
import com.angio.angiobackend.api.analyse.embeddable.GeometricAnalyse;
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
                VesselMapper.class
        })
public interface GeometricAnalyseMapper extends AbstractMapper<GeometricAnalyse, GeometricAnalyseDto> {

        @Named("updateGeometricAnalyseEntity")
        void updateEntity(GeometricAnalyseDto dto, @MappingTarget GeometricAnalyse entity);

        GeometricAnalyseReportDto toReportDto(GeometricAnalyse entity);
}
