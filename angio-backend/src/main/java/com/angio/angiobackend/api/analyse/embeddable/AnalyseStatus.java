package com.angio.angiobackend.api.analyse.embeddable;

import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AnalyseStatus implements Serializable {

    private static final long serialVersionUID = -7293042978823761724L;

    /**
     * Analyse status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private AnalyseStatusType type;

    /**
     * AnalyseStatus extension.
     */
    @Column(name = "status_extension", length = 50)
    private String extension;

    public static AnalyseStatus of(AnalyseStatusType type) {
        return new AnalyseStatus()
                .setType(type);
    }
}
