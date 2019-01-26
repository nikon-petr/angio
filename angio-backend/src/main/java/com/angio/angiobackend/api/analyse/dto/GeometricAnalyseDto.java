package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.uploads.dto.StaticFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeometricAnalyseDto implements Serializable {

    private static final long serialVersionUID = 7024774675263032596L;

    /**
     * Geometric analyse binarized image.
     */
    @ApiModelProperty("Geometric analyse binarized image")
    private StaticFileDto binarizedImage;

    /**
     * Geometric analyse skeletonized image.
     */
    @ApiModelProperty("Geometric analyse skel image")
    private StaticFileDto skeletonizedImage;

    /**
     * Vessels.
     */
    @ApiModelProperty("Vessels")
    private List<VesselDto> vessels;
}
