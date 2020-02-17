package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import com.angio.angiobackend.api.patient.dto.PatientShortItemDto;
import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(name = "Analyse id")
    private Long id;

    @ApiModelProperty(name = "Analyse name")
    private String name;

    @ApiModelProperty(name = "Analyse original image")
    private StaticFileDto originalImage;

    @ApiModelProperty(name = "Analyse short description")
    private String shortDescription;

    @ApiModelProperty(name = "Analyse type")
    private String analyseType;

    @ApiModelProperty(name = "Analyse date")
    private Date analyseDate;

    @ApiModelProperty(name = "Analyse status")
    private AnalyseStatusDto status;

    @ApiModelProperty(name = "Patient data")
    private PatientShortItemDto patient;

    @ApiModelProperty(name = "Diagnostician")
    private FullNameDto diagnostician;

    @ApiModelProperty(name = "Starred property")
    private Boolean starred;
}
