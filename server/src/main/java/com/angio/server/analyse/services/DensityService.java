package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.DensityEntity;

import java.util.Set;

public interface DensityService {
    Set<DensityEntity> addNewDensities(Set<DensityEntity> densities);
}
