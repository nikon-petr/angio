package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.CysticVolume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CysticVolumeRepository extends CrudRepository<CysticVolume, Long> {
}
