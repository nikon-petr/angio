package com.angio.angiobackend.analyse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakulaDto implements Serializable {

    /**
     * Makula id.
     */
    @ApiModelProperty(value = "Makula id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Makula area.
     */
    @ApiModelProperty("Makula area")
    private Double area;
    /**
     * Makula radius.
     */
    @ApiModelProperty("Makula radius")
    private Double radius;

    /**
     * Makula zone position x coordinate.
     */
    @ApiModelProperty("Makula zone position x coordinate")
    private Double x;

    /**
     * Makula zone position y coordinate.
     */
    @ApiModelProperty("Makula zone position y coordinate")
    private Double y;
}
