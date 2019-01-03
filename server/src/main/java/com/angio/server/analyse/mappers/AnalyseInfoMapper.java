package com.angio.server.analyse.mappers;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.requests.AnalyseInfoRequest;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class AnalyseInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(AnalyseInfoEntity.class, AnalyseInfoRequest.class)
                .byDefault()
                .register();
    }
}
