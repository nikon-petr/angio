package com.angio.server.analyse.mappers;

import com.angio.server.analyse.dto.GeometricAnalyseDto;
import com.angio.server.analyse.entities.AnalyseGeometricEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class GeometricAnalyseMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(AnalyseGeometricEntity.class, GeometricAnalyseDto.class)
                .field("binarizedImage", "binarizedImage.url")
                .field("skelImage", "skelImage.url")
                .byDefault()
                .register();
    }
}
