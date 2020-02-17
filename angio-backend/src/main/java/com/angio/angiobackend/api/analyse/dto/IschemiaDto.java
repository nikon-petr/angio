package com.angio.angiobackend.api.analyse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IschemiaDto implements Serializable {

    private static final long serialVersionUID = 796991267597889137L;

    /**
     * Ischemia id.
     */
    @ApiModelProperty(value = "Ischemia id")
    private Long id;

    /**
     * Ischemia area.
     */
    @ApiModelProperty("Ischemia area")
    private Double area;

    /**
     * Ischemia zone number.
     */
    @ApiModelProperty("Ischemia zone number")
    private Integer zoneNumber;

    /**
     * Ischemia zone position x coordinate.
     */
    @ApiModelProperty("Ischemia zone position x coordinate")
    private Double x;

    /**
     * Ischemia zone position y coordinate.
     */
    @ApiModelProperty("Ischemia zone position y coordinate")
    private Double y;
}
