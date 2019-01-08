package com.angio.angiobackend.analyse.services.impl;

import com.angio.angiobackend.analyse.entities.DensityEntity;
import com.angio.angiobackend.analyse.repositories.DensityCrudRepository;
import com.angio.angiobackend.analyse.services.DensityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service("densityService")
public class DensityServiceImpl implements DensityService {


    private final DensityCrudRepository densityCrudRepository;

    @Autowired
    public DensityServiceImpl(DensityCrudRepository densityCrudRepository) {
        this.densityCrudRepository = densityCrudRepository;
    }

    @Override
    public Set<DensityEntity> addNewDensities(Set<DensityEntity> densities) {
        return StreamSupport
                .stream(densityCrudRepository.saveAll(densities).spliterator(), false)
                .collect(Collectors.toSet());
    }
}
