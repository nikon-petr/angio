package com.angio.angiobackend.analyse.mappers;

import com.angio.angiobackend.analyse.dto.DensityDto;
import com.angio.angiobackend.analyse.entities.DensityEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class DensityMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(DensityEntity.class, DensityDto.class)
                .byDefault()
                .register();
    }
}
    