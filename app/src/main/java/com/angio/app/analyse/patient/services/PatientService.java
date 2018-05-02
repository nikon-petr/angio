package com.angio.app.analyse.patient.services;

import com.angio.app.analyse.patient.entities.PatientEntity;

public interface PatientService {
    PatientEntity getPatientByPolicy(String policy) throws Exception;
}