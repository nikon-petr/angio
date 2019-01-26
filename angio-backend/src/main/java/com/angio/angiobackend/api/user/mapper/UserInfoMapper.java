package com.angio.angiobackend.api.user.mapper;

import com.angio.angiobackend.api.analyse.dto.DiagnosticianDto;
import com.angio.angiobackend.api.user.entities.UserInfoEntity;
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
