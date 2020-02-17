package com.angio.angiobackend.api.patient.service;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientDto createPatient(PatientDto dto);

    Page<PatientDto> filterPatientsByQueryString(String search, Pageable pageable);

    Patient getPatientEntityById(Long id);

    PatientDto getPatientById(Long id);

    Patient saveOrUpdatePatient(PatientDto patient);

    PatientDto updatePatient(PatientDto dto, Long id);
}
