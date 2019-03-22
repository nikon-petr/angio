package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.analyse.type.AnalyseType;
import com.angio.angiobackend.api.analyse.validation.group.NewAnalyse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfoDto implements Serializable {

    private static final long serialVersionUID = 1122976129403952401L;

    /**
     * Patient data.
     */
    @NotNull(groups = {NewAnalyse.class})
    @Positive
    @ApiModelProperty(name = "Patient id", example = "1")
    private Long patientId;

    /**
     * Diagnostician data.
     */
    @ApiModelProperty(value = "Diagnostician data", readOnly = true)
    private DiagnosticianDto diagnostician;

    /**
     * Analyse name.
     */
    @NotNull(groups = {NewAnalyse.class}, message = "{validation.analyse.additionalInfo.name.NotNull}")
    @Size(min = 5, max = 75)
    @ApiModelProperty(value = "Analyse name", example = "Оценка сосудистой системы глазного дна")
    private String name;

    /**
     * Short description.
     */
    @NotNull(groups = {NewAnalyse.class}, message = "{validation.analyse.additionalInfo.shortDescription.NotNull}")
    @Size(min = 5, max = 100)
    @ApiModelProperty(value = "Short description", example = "Анализ глазного дна")
    private String shortDescription;

    /**
     * Full description.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Full description", example = "Анализ глазного дна")
    private String fullDescription;

    /**
     * Analyse type.
     */
    @NotNull(groups = {NewAnalyse.class})
    @ApiModelProperty(value = "Analyse type", example = "PRIMARY")
    private AnalyseType type;

    /**
     * Analyse comment.
     */
    @Size(max = 125)
    @ApiModelProperty(value = "Analyse comment", example = "Некий комментарий")
    private String comment;

    /**
     * Analyse conclusion.
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "Analyse conclusion", example = "Все плохо")
    private String conclusion;
}
