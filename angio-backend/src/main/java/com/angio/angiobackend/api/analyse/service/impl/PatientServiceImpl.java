package com.angio.angiobackend.api.analyse.service.impl;

import com.angio.angiobackend.api.analyse.entity.PatientEntity;
import com.angio.angiobackend.api.analyse.repository.PatientRepository;
import com.angio.angiobackend.api.analyse.exception.PatientExistsException;
import com.angio.angiobackend.api.analyse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("patientService")
@Transactional
public class PatientServiceImpl implements PatientService {
    PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientEntity getPatientByPolicy(String policy) throws Exception {
        PatientEntity patientEntity = patientRepository.findByPolicy(policy).stream()
                .findFirst()
                .orElse(null);

        if(patientEntity == null){
            throw new PatientExistsException("Patient does not exist.");
        }

        return patientEntity;
    }
}