package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.uploads.dto.StaticFileDto;
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

    private static final long serialVersionUID = -5207633708574755434L;

    /**
     * Vessel id.
     */
    private Long id;

    /**
     * Vessel image.
     */
    private StaticFileDto vesselImage;

    /**
     * Main vessel image.
     */
    private StaticFileDto mainVesselImage;

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
