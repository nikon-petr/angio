package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.analyse.type.DensityType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DensityDto implements Serializable {

    private static final long serialVersionUID = -8967051532216347300L;

    /**
     * Density id.
     */
    @ApiModelProperty(value = "Density id")
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

    /**
     * Density type.
     */
    @ApiModelProperty("Density type")
    private DensityType type;
}
