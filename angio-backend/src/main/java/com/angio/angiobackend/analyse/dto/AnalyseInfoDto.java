package com.angio.angiobackend.analyse.dto;

import com.angio.angiobackend.uploads.dto.UploadDto;
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
public class AnalyseInfoDto implements Serializable {

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
    @ApiModelProperty(value = "Analyse type", example = "Первичный")
    private String analyseType;

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
     * Analyse state.
     */
    @ApiModelProperty(value = "Analyse state", accessMode = READ_ONLY)
    private Boolean finished;

    /**
     * Analyse conclusion.
     */
    @ApiModelProperty(value = "Analyse conclusion", example = "Все плохо")
    private String conclusion;

    // TODO Create separate rest for image loading
    /**
     * Analyse original image
     */
    @ApiModelProperty("Analyse original image")
    private UploadDto originalImage;

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
