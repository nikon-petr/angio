package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.PatientEntity;

public interface PatientService {
    PatientEntity getPatientByPolicy(String policy) throws Exception;
}