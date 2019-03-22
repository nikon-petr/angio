package com.angio.angiobackend.api.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

    @ApiModelProperty(name = "Created user id", readOnly = true)
    private UUID id;

    @NotNull
    @Size(min = 3, max = 30)
    @ApiModelProperty(name = "New user email", example = "newuser@example.com")
    private String email;

    @NotNull
    @Size(min = 1)
    @ApiModelProperty(name = "User's role ids")
    private Set<Long> roleIds;
}
