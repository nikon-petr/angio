package com.angio.app.analyse.patient.services;

import com.angio.app.analyse.patient.entities.PatientEntity;
import com.angio.app.analyse.patient.repositories.PatientCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angio.app.security.entities.UserEntity;
import com.angio.app.security.repositories.UserCrudRepository;
import com.angio.app.userinfo.entities.UserInfoEntity;
import com.angio.app.userinfo.repositories.UserInfoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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