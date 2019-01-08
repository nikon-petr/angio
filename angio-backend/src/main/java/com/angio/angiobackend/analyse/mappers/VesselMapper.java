package com.angio.angiobackend.analyse.mappers;

import com.angio.angiobackend.analyse.dto.VesselDto;
import com.angio.angiobackend.analyse.entities.VesselEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class VesselMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(VesselEntity.class, VesselDto.class)
                .field("vesselImage", "vesselImage.url")
                .field("mainVesselImage", "mainVesselImage.url")
                .byDefault()
                .register();
    }
}
