package com.angio.angiobackend.analyse.dto;

import com.angio.angiobackend.common.dto.ImageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeometricAnalyseDto implements Serializable {

    /**
     * Geometric analyse id.
     */
    @ApiModelProperty(value = "Geometric analyse id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Geometric analyse binarized image.
     */
    @ApiModelProperty("Geometric analyse binarized image")
    private ImageDto binarizedImage;

    /**
     * Geometric analyse skel image.
     */
    @ApiModelProperty("Geometric analyse skel image")
    private ImageDto skelImage;

    /**
     * Vessels.
     */
    @ApiModelProperty("Vessels")
    private List<VesselDto> vessels;
}
