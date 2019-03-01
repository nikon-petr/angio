package com.angio.angiobackend.api.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

    @NotNull
    @ApiModelProperty(name = "Old password")
    private String oldPassword;

    @NotNull
    @Length(min = 8)
    @ApiModelProperty(name = "New password")
    private String newPassword;
}
