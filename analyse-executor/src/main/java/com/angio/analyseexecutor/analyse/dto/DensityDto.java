package com.angio.analyseexecutor.analyse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DensityDto implements Serializable {

    /**
     * Density id.
     */
    private Long id;

    /**
     * Density sector number.
     */
    private Integer sectorNumber;

    /**
     * Density value.
     */
    private Double density;
}
