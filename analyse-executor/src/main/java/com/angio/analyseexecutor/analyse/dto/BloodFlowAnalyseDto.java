package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.common.dto.UploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodFlowAnalyseDto implements Serializable {

    /**
     * Blood flow analyse id.
     */
    private Long id;

    /**
     * Ishemia image.
     */
    private UploadDto ishemiaImage;

    /**
     * Ishemias data.
     */
    private List<IshemiaDto> ischemias;

    /**
     * Makula data.
     */
    private MakulaDto makula;

    /**
     * Density image.
     */
    private UploadDto densityImage;

    /**
     * Density data.
     */
    private List<DensityDto> densities;
}
