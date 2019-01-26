package com.angio.angiobackend.api.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullNameDto implements Serializable {

    private static final long serialVersionUID = 7628488078354439386L;

    @ApiModelProperty(value = "First name", example = "Николай")
    private String firstname;

    @ApiModelProperty(value = "Last name", example = "Козловский")
    private String lastname;

    @ApiModelProperty(value = "Patronimic", example = "Николаевич")
    private String patronymic;
}
