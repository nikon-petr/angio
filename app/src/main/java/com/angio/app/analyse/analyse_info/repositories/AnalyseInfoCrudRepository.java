package com.angio.app.analyse.analyse_info.repositories;

import com.angio.app.analyse.analyse_info.entities.AnalyseInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalyseInfoCrudRepository extends CrudRepository<AnalyseInfoEntity, Long>{
    List<AnalyseInfoEntity> findAll();
}