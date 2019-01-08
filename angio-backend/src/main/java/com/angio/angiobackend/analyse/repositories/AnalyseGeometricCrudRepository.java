package com.angio.angiobackend.analyse.repositories;

import com.angio.angiobackend.analyse.entities.AnalyseGeometricEntity;
import com.angio.angiobackend.analyse.entities.AnalyseInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalyseGeometricCrudRepository extends CrudRepository<AnalyseGeometricEntity, Long> {
    List<AnalyseGeometricEntity> findByAnalyseInfo(AnalyseInfoEntity analyseInfoEntity);
}