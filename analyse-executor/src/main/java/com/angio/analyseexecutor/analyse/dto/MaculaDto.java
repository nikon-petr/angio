package com.angio.analyseexecutor.analyse.dto;

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
    private Double area;
    /**
     * Macula radius.
     */
    private Double radius;

    /**
     * Macula zone position x coordinate.
     */
    private Double x;

    /**
     * Macula zone position y coordinate.
     */
    private Double y;
}
