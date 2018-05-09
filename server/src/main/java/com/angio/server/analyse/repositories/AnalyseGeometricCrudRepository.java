package com.angio.server.analyse.repositories;

import com.angio.server.analyse.entities.AnalyseGeometricEntity;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalyseGeometricCrudRepository extends CrudRepository<AnalyseGeometricEntity, Long> {
    List<AnalyseGeometricEntity> findByAnalyseInfo(AnalyseInfoEntity analyseInfoEntity);
}