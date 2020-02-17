package com.angio.angiobackend.api.organization.dto;

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
public class OrganizationDto {

    @ApiModelProperty(name = "Organization id")
    private Long id;

    @NotNull
    @Size(max = 175)
    @ApiModelProperty(name = "Organization name")
    private String name;
}
