package com.angio.angiobackend.api.analyse.dto;

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
public class StarredAnalyseDto {

    @NotNull
    @ApiModelProperty(name = "Starred for current user", example = "true")
    private Boolean starred;
}
