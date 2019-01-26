package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VesselDto implements Serializable {

    private static final long serialVersionUID = -5207633708574755434L;

    /**
     * Vessel id.
     */
    @ApiModelProperty(value = "Vessel id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Vessel image.
     */
    @ApiModelProperty("Vessel image")
    private StaticFileDto vesselImage;

    /**
     * Main vessel image.
     */
    @ApiModelProperty("Main vessel image")
    private StaticFileDto mainVesselImage;

    /**
     * Tortuosity degree.
     */
    @ApiModelProperty("Tortuosity degree")
    private Float tortuosityDegree;

    /**
     * Count of branches.
     */
    @ApiModelProperty("Count of branches")
    private Integer countOfBranches;

    /**
     * Branching degree.
     */
    @ApiModelProperty("Branching degree")
    private Float branchingDegree;

    /**
     * Area.
     */
    @ApiModelProperty("Area")
    private Float area;

    /**
     * Area percent.
     */
    @ApiModelProperty("Area percent")
    private Float areaPercent;
}
