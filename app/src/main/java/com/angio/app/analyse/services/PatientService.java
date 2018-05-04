package com.angio.app.analyse.services;

import com.angio.app.analyse.entities.PatientEntity;

public interface PatientService {
    PatientEntity getPatientByPolicy(String policy) throws Exception;
}