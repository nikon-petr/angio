package com.angio.analyseexecutor.analyse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IshemiaDto implements Serializable {

    /**
     * Ishemia id.
     */
    private Long id;

    /**
     * Ishemia area.
     */
    private Double area;

    /**
     * Ishemia zone number.
     */
    private Integer zoneNumber;

    /**
     * Ishemia zone position x coordinate.
     */
    private Double x;

    /**
     * Ishemia zone position y coordinate.
     */
    private Double y;
}
