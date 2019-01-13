package com.angio.analyseexecutor.analyse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakulaDto implements Serializable {

    /**
     * Makula id.
     */
    private Long id;

    /**
     * Makula area.
     */
    private Double area;
    /**
     * Makula radius.
     */
    private Double radius;

    /**
     * Makula zone position x coordinate.
     */
    private Double x;

    /**
     * Makula zone position y coordinate.
     */
    private Double y;
}
