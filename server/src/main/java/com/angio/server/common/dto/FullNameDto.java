package com.angio.server.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullNameDto {

    @ApiModelProperty(value = "First name", example = "Николай")
    private String firstname;

    @ApiModelProperty(value = "Last name", example = "Козловский")
    private String lastname;

    @ApiModelProperty(value = "Patronimic", example = "Николаевич")
    private String patronymic;
}
