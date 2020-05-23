package com.angio.angiobackend.api.organization.dto;

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
public class OrganizationLockedDto {

    @NotNull
    @ApiModelProperty(name = "Organization locked")
    private Boolean locked;
}
