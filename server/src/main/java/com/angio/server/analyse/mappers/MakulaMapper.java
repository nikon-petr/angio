package com.angio.server.analyse.mappers;

import com.angio.server.analyse.dto.MakulaDto;
import com.angio.server.analyse.entities.MakulaEntity;
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
    