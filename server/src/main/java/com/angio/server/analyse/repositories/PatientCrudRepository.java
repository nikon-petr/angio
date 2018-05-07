package com.angio.server.analyse.repositories;

import com.angio.server.analyse.entities.PatientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientCrudRepository extends CrudRepository<PatientEntity, Long> {
    List<PatientEntity> findByPolicy(String policy);
}