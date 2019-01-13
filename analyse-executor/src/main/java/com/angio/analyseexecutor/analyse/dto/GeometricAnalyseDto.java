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
public class GeometricAnalyseDto implements Serializable {

    /**
     * Geometric analyse id.
     */
    private Long id;

    /**
     * Geometric analyse binarized image.
     */
    private UploadDto binarizedImage;

    /**
     * Geometric analyse skel image.
     */
    private UploadDto skelImage;

    /**
     * Vessels.
     */
    private List<VesselDto> vessels;
}
