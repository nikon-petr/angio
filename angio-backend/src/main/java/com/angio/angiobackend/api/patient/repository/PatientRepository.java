package com.angio.angiobackend.api.patient.repository;

import com.angio.angiobackend.api.patient.entity.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    Optional<Patient> findByPolicy(String policy);
}