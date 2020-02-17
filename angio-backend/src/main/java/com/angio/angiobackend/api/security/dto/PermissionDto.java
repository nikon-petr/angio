package com.angio.angiobackend.api.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

    @ApiModelProperty(name = "Permission id")
    private Long id;

    @ApiModelProperty(name = "Permission name")
    private String name;

    @ApiModelProperty(name = "Permission description")
    private String description;
}
