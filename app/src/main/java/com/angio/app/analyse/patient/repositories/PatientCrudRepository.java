package com.angio.app.analyse.patient.repositories;

import com.angio.app.analyse.patient.entities.PatientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientCrudRepository extends CrudRepository<PatientEntity, Long> {
    List<PatientEntity> findByPolicy(String policy);
}