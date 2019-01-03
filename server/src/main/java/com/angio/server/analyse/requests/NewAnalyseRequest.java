package com.angio.server.analyse.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class NewAnalyseRequest implements Serializable {

    private PatientRequest patient;
    private AnalyseInfoRequest info;
}