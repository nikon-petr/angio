package com.angio.angiobackend.api.analyse.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum AnalyseType implements Serializable {
    PRIMARY("Первичный анализ"), SUBSEQUENT("Последующий анализ");

    private String description;

    private static final long serialVersionUID = -8097058374511991671L;
}
