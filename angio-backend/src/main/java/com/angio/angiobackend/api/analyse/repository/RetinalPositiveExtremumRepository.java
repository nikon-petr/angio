package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.RetinalPositiveExtremum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetinalPositiveExtremumRepository extends CrudRepository<RetinalPositiveExtremum, Long> {
}
