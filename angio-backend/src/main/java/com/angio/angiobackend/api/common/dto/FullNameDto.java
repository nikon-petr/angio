package com.angio.angiobackend.api.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullNameDto implements Serializable {

    private static final long serialVersionUID = 7628488078354439386L;

    @NotNull(message = "{validation.common.fullName.firstname.NotNull}")
    @ApiModelProperty(value = "First name", example = "Николай")
    private String firstname;

    @NotNull(message = "{validation.common.fullName.lastname.NotNull}")
    @ApiModelProperty(value = "Last name", example = "Козловский")
    private String lastname;

    @NotNull(message = "{validation.common.fullName.patronymic.NotNull}")
    @ApiModelProperty(value = "Patronimic", example = "Николаевич")
    private String patronymic;
}
