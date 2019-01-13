package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.common.dto.UploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VesselDto implements Serializable {

    /**
     * Vessel id.
     */
    private Long id;

    /**
     * Vessel image.
     */
    private UploadDto vesselImage;

    /**
     * Main vessel image.
     */
    private UploadDto mainVesselImage;

    /**
     * Tortuosity degree.
     */
    private Float tortuosityDegree;

    /**
     * Count of branches.
     */
    private Integer countOfBranches;

    /**
     * Branching degree.
     */
    private Float branchingDegree;

    /**
     * Area.
     */
    private Float area;

    /**
     * Area percent.
     */
    private Float areaPercent;
}
