package com.angio.server.analyse.repositories;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalyseInfoCrudRepository extends CrudRepository<AnalyseInfoEntity, Long>{
    List<AnalyseInfoEntity> findAll();
}