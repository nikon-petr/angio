package com.angio.server.analyse.mappers;

import com.angio.server.analyse.dto.BloodFlowAnalyseDto;
import com.angio.server.analyse.entities.AnalyseBloodFlowEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class BloodFlowAnalyseMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(AnalyseBloodFlowEntity.class, BloodFlowAnalyseDto.class)
                .field("ishemiaImageFileName", "ishemiaImage.url")
                .field("densityImageFileName", "densityImage.url")
                .byDefault()
                .register();
    }
}
