package com.angio.angiobackend.api.patient.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientShortItemDto {

    @ApiModelProperty(name = "Patient full name")
    private FullNameDto fullName;
}
