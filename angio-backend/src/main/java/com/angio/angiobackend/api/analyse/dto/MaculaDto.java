package com.angio.angiobackend.api.analyse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaculaDto implements Serializable {

    private static final long serialVersionUID = 6262052246322071643L;

    /**
     * Macula area.
     */
    @ApiModelProperty("Makula area")
    private Double area;
    /**
     * Macula radius.
     */
    @ApiModelProperty("Makula radius")
    private Double radius;

    /**
     * Macula zone position x coordinate.
     */
    @ApiModelProperty("Makula zone position x coordinate")
    private Double x;

    /**
     * Macula zone position y coordinate.
     */
    @ApiModelProperty("Makula zone position y coordinate")
    private Double y;
}
