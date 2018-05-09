package com.angio.app.analyse.repositories;

import com.angio.app.analyse.entities.AnalyseGeometricEntity;
import com.angio.app.analyse.entities.AnalyseInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalyseGeometricCrudRepository extends CrudRepository<AnalyseGeometricEntity, Long> {
    List<AnalyseGeometricEntity> findByAnalyseInfo(AnalyseInfoEntity analyseInfoEntity);
}