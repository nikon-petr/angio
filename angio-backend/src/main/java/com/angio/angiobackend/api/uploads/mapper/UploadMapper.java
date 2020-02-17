package com.angio.angiobackend.api.uploads.mapper;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import com.angio.angiobackend.api.uploads.entity.StaticFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponents;

@Mapper
public abstract class UploadMapper {

    @Autowired
    private UriComponents uploadsUriBuilder;

    @Mapping(target = "type", defaultValue = "1")
    public abstract StaticFile toEntity(StaticFileDto dto);

    public StaticFileDto toExternalDto(StaticFile entity) {
        if (entity == null) {
            return null;
        }

        StaticFileDto staticFileDto = new StaticFileDto();

        staticFileDto.setId(entity.getId());
        staticFileDto.setFilename(entity.getFilename());
        staticFileDto.setUrl(uploadsUriBuilder.expand(entity.getFilename()).toString());

        return staticFileDto;
    }
}
