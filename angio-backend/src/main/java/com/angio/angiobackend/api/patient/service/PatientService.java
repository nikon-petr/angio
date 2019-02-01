package com.angio.angiobackend.api.patient.service;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.entity.PatientEntity;
import lombok.NonNull;

public interface PatientService {

    PatientDto createPatient(@NonNull PatientDto dto);

    PatientDto getPatientByPolicy(@NonNull String policy);

    PatientEntity getPatientEntityById(@NonNull Long id);

    PatientDto getPatientById(@NonNull Long id);

    PatientEntity saveOrUpdatePatient(@NonNull PatientDto patient);

    PatientDto updatePatient(@NonNull PatientDto dto, @NonNull Long id);
}
