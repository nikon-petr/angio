package com.angio.angiobackend.api.user.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @ApiModelProperty(name = "User id")
    private UUID id;

    @Size(max = 30)
    @ApiModelProperty(name = "User email")
    private String email;

    @Valid
    @ApiModelProperty(name = "User dull name")
    private FullNameDto fullName;
}
