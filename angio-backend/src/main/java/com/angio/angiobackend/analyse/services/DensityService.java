package com.angio.angiobackend.analyse.services;

import com.angio.angiobackend.analyse.entities.DensityEntity;

import java.util.Set;

public interface DensityService {
    Set<DensityEntity> addNewDensities(Set<DensityEntity> densities);
}
