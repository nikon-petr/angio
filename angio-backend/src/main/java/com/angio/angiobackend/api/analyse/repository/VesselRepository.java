package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.Vessel;
import org.springframework.data.repository.CrudRepository;

public interface VesselRepository extends CrudRepository<Vessel, Long> {
}