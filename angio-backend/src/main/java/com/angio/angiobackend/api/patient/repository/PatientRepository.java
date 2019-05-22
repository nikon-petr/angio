package com.angio.angiobackend.api.patient.repository;

import com.angio.angiobackend.api.patient.entity.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {
}