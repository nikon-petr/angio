package com.angio.angiobackend.uploads.mapper;

import com.angio.angiobackend.uploads.dto.UploadDto;
import com.angio.angiobackend.uploads.entity.UploadEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UploadMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UploadEntity.class, UploadDto.class)

                .fieldMap("id", "id")
                .bToA().exclude()
                .add()

                .fieldMap("filename", "filename")
                .bToA().exclude()
                .add()

                .byDefault()
                .register();
    }
}
    