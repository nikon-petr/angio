package com.angio.angiobackend.api.patient.service.impl;

import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.entity.PatientEntity;
import com.angio.angiobackend.api.patient.mapper.PatientMapper;
import com.angio.angiobackend.api.patient.repository.PatientRepository;
import com.angio.angiobackend.api.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    /**
     * Find patient by policy number or return {@link ResourceNotFoundException}.
     *
     * @param policy policy number
     * @return patient data
     */
    @Override
    public PatientDto getPatientByPolicy(@NonNull String policy) {
        log.trace("getPatientByPolicy() - start: policy={}", policy);
        return patientMapper.toDto(patientRepository.findByPolicy(policy)
                .orElseThrow(() -> new ResourceNotFoundException(format("Patient with policy=%s not found", policy))));
    }

    /**
     * Save or update patient entity if found by id.
     *
     * @param patient dto to save
     * @return saved patient entity
     */
    @Override
    public PatientEntity saveOrUpdatePatient(@NonNull PatientDto patient) {
        log.trace("saveOrUpdatePatient() - start");
        PatientEntity patientEntityFromDB = null;

        log.trace("saveOrUpdatePatient() - find patient by id");
        if (patient.getId() != null) {
            patientEntityFromDB = patientRepository.findById(patient.getId()).orElse(null);
        }

        PatientEntity patientEntity;
        if (patientEntityFromDB == null) {

            log.trace("saveOrUpdatePatient() - map patient request to new entity");
            patientEntity = patientMapper.toEntity(patient);

            log.trace("saveOrUpdatePatient() - create new patient entity: {}", patientEntity);
            patientEntity = patientRepository.save(patientEntity);
        } else {

            log.trace("saveOrUpdatePatient() - map patient request for update entity");
            patientMapper.toEntity(patient, patientEntityFromDB);

            log.trace("saveOrUpdatePatient() - save updated patient entity: {}", patientEntityFromDB);
            patientEntity = patientRepository.save(patientEntityFromDB);
        }

        log.trace("saveOrUpdatePatient() - end");
        return patientEntity;
    }
}
