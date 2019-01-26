package com.angio.angiobackend.api.analyse.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AnalyseInfoRequest implements Serializable {

    private String name;
    private String shortDescription;
    private String fullDescription;
    private String analyseType;
    private String comment;
    private String img;
}