package com.angio.app.analyse.repositories;

import com.angio.app.analyse.entities.PatientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientCrudRepository extends CrudRepository<PatientEntity, Long> {
    List<PatientEntity> findByPolicy(String policy);
}