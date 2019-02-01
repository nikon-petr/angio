package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseJmsDto implements Serializable {

    private static final long serialVersionUID = 326024071820898660L;

    /**
     * Analyse id.
     */
    @ApiModelProperty(value = "Analyse info id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Analyse original image
     */
    @ApiModelProperty("Analyse original image")
    private StaticFileDto originalImage;

    /**
     * Geometric analyse data.
     */
    @ApiModelProperty(value = "Geometric analyse data", accessMode = READ_ONLY)
    private GeometricAnalyseDto geometricAnalyse;

    /**
     * Blood flow analyse data.
     */
    @ApiModelProperty(value = "Blood flow analyse data", accessMode = READ_ONLY)
    private BloodFlowAnalyseDto bloodFlowAnalyse;
}
