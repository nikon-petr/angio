package com.angio.angiobackend.api.patient.service;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.entity.PatientEntity;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface PatientService {

    @Transactional
    PatientDto createPatient(@NonNull PatientDto dto);

    @Transactional(readOnly = true)
    PatientDto getPatientByPolicy(@NonNull String policy);

    PatientEntity getPatientEntityById(@NonNull Long id);

    @Transactional(readOnly = true)
    PatientDto getPatientById(@NonNull Long id);

    @Transactional
    PatientEntity saveOrUpdatePatient(@NonNull PatientDto patient);

    @Transactional
    PatientDto updatePatient(@NonNull PatientDto dto, @NonNull Long id);
}
