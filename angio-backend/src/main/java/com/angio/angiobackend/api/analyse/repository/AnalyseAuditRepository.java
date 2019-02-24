package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.Analyse;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyseAuditRepository extends RevisionRepository<Analyse, Long, Long> {

}
