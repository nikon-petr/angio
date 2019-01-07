package com.angio.server.user.mapper;

import com.angio.server.analyse.dto.DiagnosticianDto;
import com.angio.server.user.entities.UserInfoEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UserInfoEntity.class, DiagnosticianDto.class)
                .byDefault()
                .register();
    }
}
