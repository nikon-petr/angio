package com.angio.angiobackend.api.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

    @ApiModelProperty(name = "Old password")
    private String oldPassword;

    @ApiModelProperty(name = "New password")
    private String newPassword;
}
