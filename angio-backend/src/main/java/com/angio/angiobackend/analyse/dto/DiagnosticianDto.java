package com.angio.angiobackend.analyse.dto;

import com.angio.angiobackend.common.dto.FullNameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticianDto implements Serializable {

    /**
     * Diagnostician (user) username
     */
    private String username;

    /**
     * Diagnostician full name.
     */
    private FullNameDto fullName;
}
