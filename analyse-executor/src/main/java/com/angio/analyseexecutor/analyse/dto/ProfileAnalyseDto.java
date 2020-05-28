package com.angio.analyseexecutor.analyse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAnalyseDto implements Serializable {

    private CysticVolumeDto cysticVolume;

    private RetinalPositiveExtremumDto retinalPositiveExtremum;
}
