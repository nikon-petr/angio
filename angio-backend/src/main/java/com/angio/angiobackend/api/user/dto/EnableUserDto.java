package com.angio.angiobackend.api.user.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EnableUserDto {

    @Valid
    @NotNull
    private FullNameDto fullName;

    @NotNull
    @ApiModelProperty(name = "Enabling code")
    private String enablingCode;

    @NotNull
    @Size(min = 8, max = 120)
    @ApiModelProperty(name = "New password")
    private String newPassword;
}
