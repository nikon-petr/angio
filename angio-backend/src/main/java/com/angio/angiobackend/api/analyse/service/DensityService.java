package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.entity.DensityEntity;

import java.util.Set;

public interface DensityService {
    Set<DensityEntity> addNewDensities(Set<DensityEntity> densities);
}
