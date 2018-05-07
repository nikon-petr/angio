package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.PatientEntity;
import com.angio.server.analyse.repositories.PatientCrudRepository;
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