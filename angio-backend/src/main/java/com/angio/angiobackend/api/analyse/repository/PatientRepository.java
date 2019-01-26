package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
    List<PatientEntity> findByPolicy(String policy);
}