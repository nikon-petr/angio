package com.angio.server.analyse.dto;

import com.angio.server.common.dto.FullNameDto;
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
