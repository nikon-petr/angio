package com.angio.app.analyse.repositories;

import com.angio.app.analyse.entities.AnalyseInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalyseInfoCrudRepository extends CrudRepository<AnalyseInfoEntity, Long>{
    List<AnalyseInfoEntity> findAll();
}