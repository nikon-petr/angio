package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.entity.PatientEntity;

public interface PatientService {
    PatientEntity getPatientByPolicy(String policy) throws Exception;
}