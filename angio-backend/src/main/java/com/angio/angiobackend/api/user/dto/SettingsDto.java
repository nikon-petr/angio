package com.angio.angiobackend.api.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDto {

    @ApiModelProperty(name = "Dark theme enabled setting")
    private Boolean darkThemeEnabled;
}
