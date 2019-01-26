package com.angio.angiobackend.api.analyse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DensityDto implements Serializable {

    private static final long serialVersionUID = -8967051532216347300L;

    /**
     * Density id.
     */
    @ApiModelProperty(value = "Density id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Density sector number.
     */
    @ApiModelProperty("Density sector number")
    private Integer sectorNumber;

    /**
     * Density value.
     */
    @ApiModelProperty("Density value")
    private Double density;
}
