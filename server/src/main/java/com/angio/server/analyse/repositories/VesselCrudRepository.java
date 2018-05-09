package com.angio.server.analyse.repositories;

import com.angio.server.analyse.entities.AnalyseGeometricEntity;
import com.angio.server.analyse.entities.VesselEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VesselCrudRepository extends CrudRepository<VesselEntity, Long> {
    List<VesselEntity> findByAnalyseGeometric(AnalyseGeometricEntity analyseGeometricEntity);
}