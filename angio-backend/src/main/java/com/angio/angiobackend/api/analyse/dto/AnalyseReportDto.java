package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseReportDto {

    private Long id;

    private PatientDto patient;

    private Date analyseDate;

    private AnalyseStatusDto status;

    private StaticFileDto originalImage;

    private AdditionalInfoDto additionalInfo;

    private BloodFlowAnalyseDto bloodFlowAnalyse;

    private GeometricAnalyseReportDto geometricAnalyse;

    private ProfileAnalyseDto profileAnalyse;
}
