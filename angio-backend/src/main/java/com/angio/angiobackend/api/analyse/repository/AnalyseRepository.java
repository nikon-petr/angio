package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnalyseRepository extends JpaRepository<Analyse, Long>, JpaSpecificationExecutor<Analyse> {
}