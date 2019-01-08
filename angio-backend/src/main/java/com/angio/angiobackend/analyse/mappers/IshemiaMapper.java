package com.angio.angiobackend.analyse.mappers;

import com.angio.angiobackend.analyse.dto.IshemiaDto;
import com.angio.angiobackend.analyse.entities.IshemiaEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class IshemiaMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(IshemiaEntity.class, IshemiaDto.class)
                .byDefault()
                .register();
    }
}
    