package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseJmsDto implements Serializable {

    private static final long serialVersionUID = 326024071820898660L;

    /**
     * Analyse id.
     */
    @ApiModelProperty(value = "Analyse info id")
    private Long id;

    /**
     * Analyse status
     */
    @ApiModelProperty(value = "Analyse status")
    private AnalyseStatusDto analyseStatus;

    /**
     * Analyse execution configuration
     */
    @ApiModelProperty(value = "Analyse execution configuration")
    private ExecutionConfigurationDto executionConfiguration;

    /**
     * Analyse original image
     */
    @ApiModelProperty("Analyse original image")
    private StaticFileDto originalImage;

    /**
     * Geometric analyse data.
     */
    @ApiModelProperty(value = "Geometric analyse data")
    private GeometricAnalyseDto geometricAnalyse;

    /**
     * Blood flow analyse data.
     */
    @ApiModelProperty(value = "Blood flow analyse data")
    private BloodFlowAnalyseDto bloodFlowAnalyse;

    /**
     * Profile analyse data.
     */
    @ApiModelProperty(value = "Profile analyse data")
    private ProfileAnalyseDto profileAnalyse;
}
