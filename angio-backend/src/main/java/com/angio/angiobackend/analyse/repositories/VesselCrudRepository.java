package com.angio.angiobackend.analyse.repositories;

import com.angio.angiobackend.analyse.entities.AnalyseGeometricEntity;
import com.angio.angiobackend.analyse.entities.VesselEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VesselCrudRepository extends CrudRepository<VesselEntity, Long> {
    List<VesselEntity> findByAnalyseGeometric(AnalyseGeometricEntity analyseGeometricEntity);
}