package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.analyse.type.AnalyseType;
import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.security.entities.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfoDto implements Serializable {

    private static final long serialVersionUID = 1122976129403952401L;

    /**
     * Patient data.
     */
    @ApiModelProperty(name = "Patient id", example = "1")
    private Long patientId;

    /**
     * Diagnostician data.
     */
    @ApiModelProperty(value = "Diagnostician data", accessMode = READ_ONLY)
    private DiagnosticianDto diagnostician;

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
     * Analyse comment.
     */
    @ApiModelProperty(value = "Analyse comment", example = "Некий комментарий")
    private String comment;

    /**
     * Analyse conclusion.
     */
    @ApiModelProperty(value = "Analyse conclusion", example = "Все плохо")
    private String conclusion;

}
