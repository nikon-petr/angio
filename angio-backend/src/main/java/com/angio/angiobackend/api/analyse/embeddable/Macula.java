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
public class Macula {

    @Column(name = "macula_area", nullable = false)
    private Double area;

    @Column(name = "macula_radius", nullable = false)
    private Double radius;

    @Column(name = "macula_center_x", nullable = false)
    private Double x;

    @Column(name = "macula_center_y", nullable = false)
    private Double y;
}
