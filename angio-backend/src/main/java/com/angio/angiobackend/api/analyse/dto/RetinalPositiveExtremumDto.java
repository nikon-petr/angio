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
public class RetinalPositiveExtremumDto {

    @ApiModelProperty("Retinal positive extremum id")
    private Long id;

    @ApiModelProperty("Retinal positive extremum profile image")
    private StaticFileDto profileImage;

    @ApiModelProperty("Retinal positive extremum angiogram image")
    private StaticFileDto angiogramImage;

    @ApiModelProperty("Retinal positive extremum value")
    private Double extremumValue;

}
