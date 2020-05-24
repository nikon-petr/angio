package com.angio.angiobackend.api.dashboard.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDto {

    @ApiModelProperty(name = "Analyse statistics")
    public AnalyseStatisticsDto analyse;

    @ApiModelProperty(name = "User statistics")
    public UserStatisticsDto user;

    @ApiModelProperty(name = "Organization statistics")
    public OrganizationStatisticsDto organization;
}
