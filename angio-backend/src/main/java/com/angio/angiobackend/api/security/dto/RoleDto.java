package com.angio.angiobackend.api.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @ApiModelProperty(name = "Role id")
    private Long id;

    @ApiModelProperty(name = "Role name")
    private String name;

    @ApiModelProperty(name = "Role description")
    private String description;

    @ApiModelProperty(name = "Role permissions")
    private List<PermissionDto> permissions;
}
