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
public class ResetUserDto {

    @NotNull
    @ApiModelProperty(name = "Reset code")
    private String resetCode;

    @NotNull
    @Size(min = 8)
    @ApiModelProperty(name = "New password")
    private String newPassword;
}
