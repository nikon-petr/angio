package com.angio.angiobackend.api.patient.service;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.entity.Patient;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface PatientService {

    @Transactional
    PatientDto createPatient(@NonNull PatientDto dto);

    @Transactional(readOnly = true)
    Page<PatientDto> filterPatientsByQueryString(String search, Pageable pageable);

    @Transactional(readOnly = true)
    Patient getPatientEntityById(@NonNull Long id);

    @Transactional(readOnly = true)
    PatientDto getPatientById(@NonNull Long id);

    @Transactional
    Patient saveOrUpdatePatient(@NonNull PatientDto patient);

    @Transactional
    PatientDto updatePatient(@NonNull PatientDto dto, @NonNull Long id);
}
