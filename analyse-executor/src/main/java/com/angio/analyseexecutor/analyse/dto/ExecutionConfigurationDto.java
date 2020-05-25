package com.angio.analyseexecutor.analyse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionConfigurationDto {

    /**
     * Execute analyse for macula blood flow
     */
    private Boolean maculaBloodFlow;

    /**
     * Execute analyse for optic disk blood flow density
     */
    private Boolean opticDiskBloodFlow;

    /**
     * Execute analyse for tortuosity and branching
     */
    private Boolean geometric;

    /**
     * Execute analyse for profile cystic volume
     */
    private Boolean profileCysticVolume;

    /**
     * Execute analyse for profile retinal positive extremum
     */
    private Boolean profileRetinalPositiveExtremum;
}
