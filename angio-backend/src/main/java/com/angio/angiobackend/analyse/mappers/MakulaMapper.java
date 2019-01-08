package com.angio.angiobackend.analyse.mappers;

import com.angio.angiobackend.analyse.dto.MakulaDto;
import com.angio.angiobackend.analyse.entities.MakulaEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class MakulaMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(MakulaEntity.class, MakulaDto.class)
                .byDefault()
                .register();
    }
}
    