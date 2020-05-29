package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CysticVolumeDto {

    @ApiModelProperty("Cystic volume id")
    private Long id;

    @ApiModelProperty("Cystic volume profile image")
    private StaticFileDto profileImage;

    @ApiModelProperty("Cystic volume angiogram image")
    private StaticFileDto angiogramImage;

    @ApiModelProperty("Cystic volume")
    private Double cysticVolume;
}
