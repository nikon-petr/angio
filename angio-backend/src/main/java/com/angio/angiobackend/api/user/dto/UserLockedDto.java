package com.angio.angiobackend.api.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserLockedDto {

    @NotNull
    @ApiModelProperty(name = "User locked")
    private Boolean locked;
}
