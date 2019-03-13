package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.uploads.dto.StaticFileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseDto implements Serializable {

    private static final long serialVersionUID = 326024071820898660L;

    /**
     * Analyse id.
     */
    private Long id;

    /**
     * Analyse original image
     */
    private StaticFileDto originalImage;

    /**
     * Geometric analyse data.
     */
    private GeometricAnalyseDto geometricAnalyse;

    /**
     * Blood flow analyse data.
     */
    private BloodFlowAnalyseDto bloodFlowAnalyse;
}
