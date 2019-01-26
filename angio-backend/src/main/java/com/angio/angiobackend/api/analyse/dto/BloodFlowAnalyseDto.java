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
public class BloodFlowAnalyseDto implements Serializable {

    private static final long serialVersionUID = 733306430668481345L;

    /**
     * Ishemia image.
     */
    @ApiModelProperty("Ischemia image")
    private StaticFileDto ischemiaImage;

    /**
     * Ishemias data.
     */
    @ApiModelProperty("Ischemias data")
    private List<IschemiaDto> ischemias;

    /**
     * Makula data.
     */
    @ApiModelProperty("Makula data")
    private MaculaDto macula;

    /**
     * Density image.
     */
    @ApiModelProperty("Density image")
    private StaticFileDto densityImage;

    /**
     * Density data.
     */
    @ApiModelProperty("Density data")
    private List<DensityDto> densities;
}
