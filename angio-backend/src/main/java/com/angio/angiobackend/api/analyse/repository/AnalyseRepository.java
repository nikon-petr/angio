package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnalyseRepository extends JpaRepository<AnalyseEntity, Long> {

    Optional<AnalyseEntity> findById(Long id);
    Optional<AnalyseEntity> findOne(Specification<AnalyseEntity> specification);
    Page<AnalyseEntity> findAll(Specification<AnalyseEntity> spec, Pageable pegeable);
    List<AnalyseEntity> findAll(Specification<AnalyseEntity> spec);
}