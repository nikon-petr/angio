package com.angio.angiobackend.api.patient.repository;

import com.angio.angiobackend.api.patient.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
    Optional<PatientEntity> findByPolicy(String policy);
}