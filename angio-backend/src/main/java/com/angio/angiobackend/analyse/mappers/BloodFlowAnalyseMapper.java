package com.angio.angiobackend.analyse.mappers;

import com.angio.angiobackend.analyse.dto.BloodFlowAnalyseDto;
import com.angio.angiobackend.analyse.entities.AnalyseBloodFlowEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class BloodFlowAnalyseMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(AnalyseBloodFlowEntity.class, BloodFlowAnalyseDto.class)
                .field("ishemiaImageFileName", "ishemiaImage.uri")
                .field("densityImageFileName", "densityImage.uri")
                .byDefault()
                .register();
    }
}
