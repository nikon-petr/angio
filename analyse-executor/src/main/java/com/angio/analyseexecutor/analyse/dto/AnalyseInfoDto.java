package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.common.dto.UploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseInfoDto implements Serializable {

    /**
     * Analyse id.
     */
    private Long id;

    /**
     * Analyse name.
     */
    private String name;

    /**
     * Short description.
     */
    private String shortDescription;

    /**
     * Full description.
     */
    private String fullDescription;

    /**
     * Analyse type.
     */
    private String analyseType;

    /**
     * Analyse date.
     */
    private Date analyseDate;

    /**
     * Analyse comment.
     */
    private String comment;

    /**
     * Analyse state.
     */
    private Boolean finished;

    /**
     * Analyse conclusion.
     */
    private String conclusion;

    // TODO Create separate rest for image loading
    /**
     * Analyse original image
     */
    private UploadDto originalImage;

    /**
     * Patient data.
     */
    private PatientDto patient;

    /**
     * Diagnostician data.
     */
    private DiagnosticianDto diagnostician;

    /**
     * Geometric analyse data.
     */
    private GeometricAnalyseDto geometricAnalyse;

    /**
     * Blood flow analyse data.
     */
    private BloodFlowAnalyseDto bloodFlowAnalyse;
}
