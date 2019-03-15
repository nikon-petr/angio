package com.angio.angiobackend.api.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EnableUserDto {

    @NotNull
    @Size(min = 1, max = 30)
    @ApiModelProperty(name = "User first name")
    private String firstname;

    @NotNull
    @Size(min = 1, max = 30)
    @ApiModelProperty(name = "User last name")
    private String lastname;

    @Size(max = 30)
    @ApiModelProperty(name = "User patronymic name")
    private String patronymic;

    @NotNull
    @ApiModelProperty(name = "Enabling code")
    private String enablingCode;

    @NotNull
    @Size(min = 8, max = 120)
    @ApiModelProperty(name = "New password")
    private String newPassword;
}
