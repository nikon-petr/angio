package com.angio.server.analyse.dto;

import com.angio.server.common.dto.ImageDto;
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

    /**
     * Vessel id.
     */
    @ApiModelProperty(value = "Vessel id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Vessel image.
     */
    @ApiModelProperty("Vessel image")
    private ImageDto vesselImage;

    /**
     * Main vessel image.
     */
    @ApiModelProperty("Main vessel image")
    private ImageDto mainVesselImage;

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
