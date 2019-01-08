package com.angio.angiobackend.analyse.services;

import com.angio.angiobackend.analyse.entities.PatientEntity;

public interface PatientService {
    PatientEntity getPatientByPolicy(String policy) throws Exception;
}