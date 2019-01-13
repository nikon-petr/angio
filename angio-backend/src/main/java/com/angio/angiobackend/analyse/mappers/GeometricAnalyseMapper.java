package com.angio.angiobackend.analyse.mappers;

import com.angio.angiobackend.analyse.dto.GeometricAnalyseDto;
import com.angio.angiobackend.analyse.entities.AnalyseGeometricEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class GeometricAnalyseMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(AnalyseGeometricEntity.class, GeometricAnalyseDto.class)
                .field("binarizedImage", "binarizedImage.uri")
                .field("skelImage", "skelImage.uri")
                .byDefault()
                .register();
    }
}
