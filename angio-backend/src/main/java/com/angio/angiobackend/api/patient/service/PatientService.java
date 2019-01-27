package com.angio.angiobackend.api.patient.service;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.entity.PatientEntity;

public interface PatientService {

    PatientDto getPatientByPolicy(String policy);
    PatientEntity saveOrUpdatePatient(PatientDto patient);
}
