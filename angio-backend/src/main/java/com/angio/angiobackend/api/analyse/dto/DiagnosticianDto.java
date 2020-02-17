package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticianDto implements Serializable {

    private static final long serialVersionUID = 6362808884737512182L;

    /**
     * Diagnostician (user) username
     */
    @ApiModelProperty("Diagnostician username")
    private String username;

    /**
     * Diagnostician full name.
     */
    @ApiModelProperty("Diagnostician fullname")
    private FullNameDto fullName;
}
