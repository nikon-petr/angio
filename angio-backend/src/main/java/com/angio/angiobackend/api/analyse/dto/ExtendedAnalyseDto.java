package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.analyse.type.AnalyseType;
import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedAnalyseDto implements Serializable {

    private static final long serialVersionUID = -1337462575059463975L;

    /**
     * Analyse id.
     */
    @ApiModelProperty(value = "Analyse info id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Analyse name.
     */
    @ApiModelProperty(value = "Analyse name", example = "Оценка сосудистой системы глазного дна")
    private String name;

    /**
     * Short description.
     */
    @ApiModelProperty(value = "Short description", example = "Анализ глазного дна")
    private String shortDescription;

    /**
     * Full description.
     */
    @ApiModelProperty(value = "Full description", example = "Анализ глазного дна")
    private String fullDescription;

    /**
     * Analyse type.
     */
    @ApiModelProperty(value = "Analyse type", example = "PRIMARY")
    private AnalyseType type;

    /**
     * Analyse date.
     */
    @ApiModelProperty(value = "Analyse name", accessMode = READ_ONLY)
    private Date analyseDate;

    /**
     * Analyse comment.
     */
    @ApiModelProperty(value = "Analyse comment", example = "Некий комментарий")
    private String comment;

    /**
     * Analyse status.
     */
    @ApiModelProperty(value = "Analyse status", accessMode = READ_ONLY)
    private AnalyseStatusDto status;

    /**
     * Analyse conclusion.
     */
    @ApiModelProperty(value = "Analyse conclusion", example = "Все плохо")
    private String conclusion;

    /**
     * Analyse original image
     */
    @ApiModelProperty("Analyse original image")
    private StaticFileDto originalImage;

    /**
     * Patient data.
     */
    @ApiModelProperty("Patient data")
    private PatientDto patient;

    /**
     * Diagnostician data.
     */
    @ApiModelProperty(value = "Diagnostician data", accessMode = READ_ONLY)
    private DiagnosticianDto diagnostician;

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
