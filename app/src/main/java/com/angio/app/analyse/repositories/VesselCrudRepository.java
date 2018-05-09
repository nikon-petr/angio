package com.angio.app.analyse.repositories;

import com.angio.app.analyse.entities.AnalyseGeometricEntity;
import com.angio.app.analyse.entities.VesselEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VesselCrudRepository extends CrudRepository<VesselEntity, Long> {
    List<VesselEntity> findByAnalyseGeometric(AnalyseGeometricEntity analyseGeometricEntity);
}