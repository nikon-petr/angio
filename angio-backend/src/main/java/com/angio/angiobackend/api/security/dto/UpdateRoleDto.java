package com.angio.angiobackend.api.security.dto;

import com.angio.angiobackend.api.security.validation.group.NewRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {

    @NotNull(groups = NewRole.class)
    @ApiModelProperty(name = "Role id")
    private Long id;

    @NotNull(groups = NewRole.class)
    @Size(min = 3, max = 30)
    @ApiModelProperty(name = "Role description")
    private String description;

    @NotNull(groups = NewRole.class)
    @Size(min = 1)
    @ApiModelProperty(name = "Role permission ids")
    private List<Long> permissionIds;
}
