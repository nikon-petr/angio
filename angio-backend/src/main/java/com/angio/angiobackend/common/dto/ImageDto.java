package com.angio.angiobackend.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto implements Serializable {

    /**
     * Image id.
     */
    @ApiModelProperty("Image id")
    private Long id;

    /**
     * Image url.
     */
    @ApiModelProperty(value = "Image url", accessMode = READ_ONLY)
    private String url;
}
