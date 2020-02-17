package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GeometricAnalyseReportDto {

    private StaticFileDto binarizedImage;

    private StaticFileDto skeletonizedImage;

    private Long vesselsCount;

    private Long branchesCount;

    private Double tortuosityDegreeAvg;

    private Double branchingDegreeAvg;

    private Double areaSumPx;

    private Double areaSumPercent;
}
