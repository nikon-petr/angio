package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnalyseStatusDto implements Serializable {

    private static final long serialVersionUID = -8097058851511448671L;

    @ApiModelProperty(value = "Analyse status type", example = "CREATED", accessMode = READ_ONLY)
    private AnalyseStatusType type;

    @ApiModelProperty(value = "Analyse status extension", accessMode = READ_ONLY)
    private String extension;
}
