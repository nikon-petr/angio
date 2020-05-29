package com.angio.analyseexecutor.analyse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DensityDto implements Serializable {

    private static final long serialVersionUID = -8967051532216347300L;

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

    /**
     * Density type.
     */
    private DensityType type;
}
