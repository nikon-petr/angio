package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import com.angio.angiobackend.api.patient.dto.PatientShortItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnalyseShortItemDto {

    private Long id;
    private String name;
    private String shortDescription;
    private String analyseType;
    private Date analyseDate;
    private AnalyseStatusDto status;
    private PatientShortItemDto patient;
    private FullNameDto diagnostician;
    private Boolean starred;
}
