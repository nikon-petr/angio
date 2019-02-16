package com.angio.angiobackend.api.patient.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.entity.Patient;
import com.angio.angiobackend.api.patient.mapper.PatientMapper;
import com.angio.angiobackend.api.patient.repository.PatientRepository;
import com.angio.angiobackend.api.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final DynamicLocaleMessageSourceAccessor msa;

    /**
     * Create new patient.
     *
     * @param dto patient data
     * @return saved patient data
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('PATIENT_CREATE')")
    public PatientDto createPatient(@NonNull PatientDto dto) {
        log.trace("getPatientByPolicy() - start: patient={}", dto);
        Patient entity = patientMapper.toEntity(dto);

        log.trace("getPatientByPolicy() - save patient to db");
        entity = patientRepository.save(entity);

        log.trace("getPatientByPolicy() - end");
        return patientMapper.toDto(entity);
    }

    /**
     * Find patient by policy number or throw {@link ResourceNotFoundException}.
     *
     * @param policy policy number
     * @return patient data
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('PATIENT_VIEW')")
    public PatientDto getPatientByPolicy(@NonNull String policy) {
        log.trace("getPatientByPolicy() - start: policy={}", policy);
        return patientMapper.toDto(patientRepository.findByPolicy(policy)
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.patient.policy.notFound", new String[] {policy}))));
    }

    /**
     * Find patient by id and return entity object or throw {@link ResourceNotFoundException}.
     *
     * @param id patient id
     * @return patient entity
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('PATIENT_VIEW')")
    public Patient getPatientEntityById(@NonNull Long id) {
        log.trace("getPatientByPolicy() - start: id={}", id);
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.patient.notFound", new Object[] {id})));
    }

    /**
     * Find patient by id and return dto or throw {@link ResourceNotFoundException}.
     *
     * @param id patient id
     * @return patient dto
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('PATIENT_VIEW')")
    public PatientDto getPatientById(@NonNull Long id) {
        return patientMapper.toDto(getPatientEntityById(id));
    }

    /**
     * Save or update patient entity if found by id.
     *
     * @param dto dto to save
     * @return saved patient entity
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('PATIENT_EDIT')")
    public Patient saveOrUpdatePatient(@NonNull PatientDto dto) {
        log.trace("saveOrUpdatePatient() - start");
        Patient patientFromDb = null;

        log.trace("saveOrUpdatePatient() - find patient by id");
        if (dto.getId() != null) {
            patientFromDb = patientRepository.findById(dto.getId()).orElse(null);
        }

        Patient patient;
        if (patientFromDb == null) {

            log.trace("saveOrUpdatePatient() - map patient request to new entity");
            patient = patientMapper.toEntity(dto);

            log.trace("saveOrUpdatePatient() - create new patient entity: {}", patient);
            patient = patientRepository.save(patient);
        } else {

            log.trace("saveOrUpdatePatient() - map patient request for update entity");
            patientMapper.toEntity(dto, patientFromDb);

            log.trace("saveOrUpdatePatient() - save updated patient entity: {}", patientFromDb);
            patient = patientRepository.save(patientFromDb);
        }

        log.trace("saveOrUpdatePatient() - end");
        return patient;
    }

    /**
     * Update one or more patient fields and return result.
     *
     * @param dto patient data
     * @param id patient id
     * @return updated patient data
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('PATIENT_EDIT')")
    public PatientDto updatePatient(@NonNull PatientDto dto, @NonNull Long id) {
        log.trace("updatePatient() - start: patient={}", dto);
        Patient entity = getPatientEntityById(id);

        log.trace("updatePatient() - map patient dto to entity");
        patientMapper.toEntity(dto, entity);

        log.trace("updatePatient() - save patient to db");
        entity = patientRepository.save(entity);

        log.trace("updatePatient() - end");
        return patientMapper.toDto(entity);
    }
}
