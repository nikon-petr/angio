package com.angio.angiobackend.api.analyse.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ExecutionConfiguration {

    @Column(name = "macula_blood_flow", nullable = false)
    private Boolean maculaBloodFlow;

    @Column(name = "optic_disk_blood_flow", nullable = false)
    private Boolean opticDiskBloodFlow;

    @Column(name = "geometric", nullable = false)
    private Boolean geometric;

    @Column(name = "profile_cystic_volume", nullable = false)
    private Boolean profileCysticVolume;

    @Column(name = "profile_retinal_positive_extremum", nullable = false)
    private Boolean profileRetinalPositiveExtremum;
}
