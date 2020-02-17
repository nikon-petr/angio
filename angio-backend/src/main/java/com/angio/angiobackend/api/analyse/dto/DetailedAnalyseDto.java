package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.analyse.validation.group.NewAnalyse;
import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedAnalyseDto implements Serializable {

    private static final long serialVersionUID = -1337462575059463975L;

    /**
     * Analyse id.
     */
    @ApiModelProperty(value = "Analyse info id", readOnly = true)
    private Long id;

    /**
     * Analyse date.
     */
    @ApiModelProperty(value = "Analyse name", readOnly = true)
    private Date analyseDate;

    /**
     * Analyse status.
     */
    @ApiModelProperty(value = "Analyse status", readOnly = true)
    private AnalyseStatusDto status;

    /**
     * Analyse original image
     */
    @Valid
    @NotNull(groups = {NewAnalyse.class})
    @ApiModelProperty("Analyse original image")
    private StaticFileDto originalImage;

    /**
     * Additional analyse info.
     */
    @Valid
    @NotNull(groups = {NewAnalyse.class})
    @ApiModelProperty("Additional analyse info")
    private AdditionalInfoDto additionalInfo;

    /**
     * Geometric analyse data.
     */
    @ApiModelProperty(value = "Geometric analyse data", readOnly = true)
    private GeometricAnalyseDto geometricAnalyse;

    /**
     * Blood flow analyse data.
     */
    @ApiModelProperty(value = "Blood flow analyse data", readOnly = true)
    private BloodFlowAnalyseDto bloodFlowAnalyse;
}
