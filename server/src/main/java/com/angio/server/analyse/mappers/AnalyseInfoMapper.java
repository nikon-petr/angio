package com.angio.server.analyse.mappers;

import com.angio.server.analyse.dto.AnalyseInfoDto;
import com.angio.server.analyse.dto.AnalyseShortItemDto;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.requests.AnalyseInfoRequest;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.metadata.MappingDirection;
import org.springframework.stereotype.Component;

@Component
public class AnalyseInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {

        // TODO remove when complete rest api v2
        factory.classMap(AnalyseInfoEntity.class, AnalyseInfoRequest.class)
                .byDefault()
                .register();

        factory.classMap(AnalyseInfoEntity.class, AnalyseShortItemDto.class)
                .field("user.userInfo.fullName", "diagnostician")
                .byDefault()
                .register();

        factory.classMap(AnalyseInfoEntity.class, AnalyseInfoDto.class)

                .fieldMap("id")
                .direction(MappingDirection.A_TO_B)
                .add()

                .fieldMap("user.userInfo", "diagnostician")
                .direction(MappingDirection.A_TO_B)
                .add()

                .field("user.username", "diagnostician.username")

                // TODO create converter
                .field("img", "originalImage.url")

                .fieldMap("finished")
                .direction(MappingDirection.A_TO_B)
                .add()

                .fieldMap("analyseGeometric", "geometricAnalyse")
                .direction(MappingDirection.A_TO_B)
                .add()

                .fieldMap("analyseBloodFlow", "bloodFlowAnalyse")
                .direction(MappingDirection.A_TO_B)
                .add()

                .byDefault()
                .register();
    }
}
