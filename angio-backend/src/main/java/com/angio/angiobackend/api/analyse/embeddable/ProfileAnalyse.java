package com.angio.angiobackend.api.analyse.embeddable;

import com.angio.angiobackend.api.analyse.entity.Analyse;
import com.angio.angiobackend.api.analyse.entity.CysticVolume;
import com.angio.angiobackend.api.analyse.entity.RetinalPositiveExtremum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"cysticVolume", "retinalPositiveExtremum", "analyse"})
@EqualsAndHashCode(exclude = {"cysticVolume", "retinalPositiveExtremum", "analyse"})
@Embeddable
@Access(AccessType.FIELD)
public class ProfileAnalyse {

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "analyse",
            orphanRemoval = true
    )
    private CysticVolume cysticVolume;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "analyse",
            orphanRemoval = true
    )
    private RetinalPositiveExtremum retinalPositiveExtremum;

    @Transient
    private Analyse analyse;

    public void setCysticVolume(CysticVolume cysticVolume) {
        this.cysticVolume = cysticVolume;
        if (cysticVolume != null) {
            cysticVolume.setAnalyse(analyse);
        }
    }

    public void setRetinalPositiveExtremum(RetinalPositiveExtremum retinalPositiveExtremum) {
        this.retinalPositiveExtremum = retinalPositiveExtremum;
        if (retinalPositiveExtremum != null) {
            retinalPositiveExtremum.setAnalyse(analyse);
        }
    }
}
