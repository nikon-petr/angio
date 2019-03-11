package com.angio.angiobackend.api.user.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import com.angio.angiobackend.api.security.dto.RoleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {

    @ApiModelProperty(name = "User id")
    private UUID id;

    @ApiModelProperty(name = "User email")
    private String email;

    @ApiModelProperty(name = "User dull name")
    private FullNameDto fullName;

    @ApiModelProperty(name = "User locked")
    private Boolean locked;

    @ApiModelProperty(name = "User enabled")
    private Boolean enabled;

    @ApiModelProperty(name = "User roles")
    private List<RoleDto> roles;

    @ApiModelProperty(name = "User owned roles to manage")
    private List<RoleDto> ownedRolesToManage;
}