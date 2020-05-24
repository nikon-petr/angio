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
public class DashboardDto {

    @ApiModelProperty(name = "Statistics")
    public StatisticsDto statistics;
}
