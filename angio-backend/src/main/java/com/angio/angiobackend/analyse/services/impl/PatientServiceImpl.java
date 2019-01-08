package com.angio.angiobackend.analyse.services.impl;

import com.angio.angiobackend.analyse.entities.PatientEntity;
import com.angio.angiobackend.analyse.repositories.PatientCrudRepository;
import com.angio.angiobackend.analyse.exception.PatientExistsException;
import com.angio.angiobackend.analyse.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("patientService")
@Transactional
public class PatientServiceImpl implements PatientService {
    PatientCrudRepository patientCrudRepository;

    @Autowired
    public PatientServiceImpl(PatientCrudRepository patientCrudRepository) {
        this.patientCrudRepository = patientCrudRepository;
    }

    @Override
    public PatientEntity getPatientByPolicy(String policy) throws Exception {
        PatientEntity patientEntity = patientCrudRepository.findByPolicy(policy).stream()
                .findFirst()
                .orElse(null);

        if(patientEntity == null){
            throw new PatientExistsException("Patient does not exist.");
        }

        return patientEntity;
    }
}