package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnalyseRepository extends JpaRepository<AnalyseEntity, Long>, JpaSpecificationExecutor<AnalyseEntity> {
}