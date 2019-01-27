package com.angio.angiobackend.api.patient.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientShortItemDto {

    private FullNameDto fullName;
    private String policy;
}
