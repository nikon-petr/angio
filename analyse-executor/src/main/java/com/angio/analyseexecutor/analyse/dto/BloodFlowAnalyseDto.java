package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.uploads.dto.StaticFileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BloodFlowAnalyseDto implements Serializable {

    private static final long serialVersionUID = 733306430668481345L;

    /**
     * Ishemia image.
     */
    private StaticFileDto ischemiaImage;

    /**
     * Ishemias data.
     */
    private List<IschemiaDto> ischemias;

    /**
     * Makula data.
     */
    private MaculaDto macula;

    /**
     * Density image.
     */
    private StaticFileDto densityImage;

    /**
     * Density data.
     */
    private List<DensityDto> densities;
}
