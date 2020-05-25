package com.angio.angiobackend.api.analyse.dto;

import com.angio.angiobackend.api.analyse.validation.AnalyseExecutionConfigurationNonConflict;
import com.angio.angiobackend.api.analyse.validation.group.NewAnalyse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@AnalyseExecutionConfigurationNonConflict(groups = NewAnalyse.class)
public class ExecutionConfigurationDto {

    /**
     * Execute analyse for macula blood flow
     */
    @NotNull(groups = {NewAnalyse.class})
    @ApiModelProperty(value = "Execute analyse for macula blood flow", example = "true")
    private Boolean maculaBloodFlow;

    /**
     * Execute analyse for optic disk blood flow density
     */
    @NotNull(groups = {NewAnalyse.class})
    @ApiModelProperty(value = "Execute analyse for optic disk blood flow density", example = "false")
    private Boolean opticDiskBloodFlow;

    /**
     * Execute analyse for tortuosity and branching
     */
    @NotNull(groups = {NewAnalyse.class})
    @ApiModelProperty(value = "Execute analyse for tortuosity and branching", example = "false")
    private Boolean geometric;

    /**
     * Execute analyse for profile cystic volume
     */
    @NotNull(groups = {NewAnalyse.class})
    @ApiModelProperty(value = "Execute analyse for cystic volume", example = "false")
    private Boolean profileCysticVolume;

    /**
     * Execute analyse for profile retinal positive extremum
     */
    @NotNull(groups = {NewAnalyse.class})
    @ApiModelProperty(value = "Execute analyse for retinal positive extremum", example = "false")
    private Boolean profileRetinalPositiveExtremum;
}
