package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.uploads.dto.StaticFileDto;
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
    private StaticFileDto binarizedImage;

    /**
     * Geometric analyse skel image.
     */
    private StaticFileDto skeletonizedImage;

    /**
     * Vessels.
     */
    private List<VesselDto> vessels;
}
