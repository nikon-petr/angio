package com.angio.server.analyse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IshemiaDto implements Serializable {

    /**
     * Ishemia id.
     */
    @ApiModelProperty(value = "Ishemia id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Ishemia area.
     */
    @ApiModelProperty("Ishemia area")
    private Double area;

    /**
     * Ishemia zone number.
     */
    @ApiModelProperty("Ishemia zone number")
    private Integer zoneNumber;

    /**
     * Ishemia zone position x coordinate.
     */
    @ApiModelProperty("Ishemia zone position x coordinate")
    private Double x;

    /**
     * Ishemia zone position y coordinate.
     */
    @ApiModelProperty("Ishemia zone position y coordinate")
    private Double y;
}
