package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Date;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedAnalyseDto implements Serializable {

    private static final long serialVersionUID = -1337462575059463975L;

    /**
     * Analyse id.
     */
    @ApiModelProperty(value = "Analyse info id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Analyse date.
     */
    @ApiModelProperty(value = "Analyse name", accessMode = READ_ONLY)
    private Date analyseDate;

    /**
     * Analyse status.
     */
    @ApiModelProperty(value = "Analyse status", accessMode = READ_ONLY)
    private AnalyseStatusDto status;

    /**
     * Analyse original image
     */
    @ApiModelProperty("Analyse original image")
    private StaticFileDto originalImage;

    /**
     * Additional analyse info.
     */
    @Valid
    @ApiModelProperty("Additional analyse info")
    private AdditionalInfoDto additionalInfo;

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
