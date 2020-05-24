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
public class AnalyseStatisticsDto {

    @ApiModelProperty(name = "Number of analyses")
    public Long totalCount;

    @ApiModelProperty(name = "Number of successfully completed analyses")
    public Long successCount;

    @ApiModelProperty(name = "Percent of successfully completed analyses")
    public Double successPercent;

    @ApiModelProperty(name = "Number of unsuccessfully completed analyses")
    public Long failedCount;

    @ApiModelProperty(name = "Percent of unsuccessfully completed analyses")
    public Double failedPercent;
}
