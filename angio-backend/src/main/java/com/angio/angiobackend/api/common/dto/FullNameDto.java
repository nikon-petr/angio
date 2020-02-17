package com.angio.angiobackend.api.common.dto;

import com.angio.angiobackend.api.common.validation.group.NewFullName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullNameDto implements Serializable {

    private static final long serialVersionUID = 7628488078354439386L;

    @NotNull(groups = {NewFullName.class})
    @Size(min = 1, max = 30)
    @ApiModelProperty(value = "First name", example = "Николай")
    private String firstname;

    @NotNull(groups = {NewFullName.class})
    @Size(min = 1, max = 30)
    @ApiModelProperty(value = "Last name", example = "Козловский")
    private String lastname;

    @Size(max = 30)
    @ApiModelProperty(value = "Patronimic", example = "Николаевич")
    private String patronymic;
}
