package com.angio.angiobackend.analyse.dto;

import com.angio.angiobackend.uploads.dto.UploadDto;
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
public class BloodFlowAnalyseDto implements Serializable {

    /**
     * Blood flow analyse id.
     */
    @ApiModelProperty(value = "Blood flow analyse id", accessMode = READ_ONLY)
    private Long id;

    /**
     * Ishemia image.
     */
    @ApiModelProperty("Ishemia image")
    private UploadDto ishemiaImage;

    /**
     * Ishemias data.
     */
    @ApiModelProperty("Ishemias data")
    private List<IshemiaDto> ischemias;

    /**
     * Makula data.
     */
    @ApiModelProperty("Makula data")
    private MakulaDto makula;

    /**
     * Density image.
     */
    @ApiModelProperty("Density image")
    private UploadDto densityImage;

    /**
     * Density data.
     */
    @ApiModelProperty("Density data")
    private List<DensityDto> densities;
}
