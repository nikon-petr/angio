package com.angio.angiobackend.api.analyse.service.impl;

import com.angio.angiobackend.api.analyse.entity.DensityEntity;
import com.angio.angiobackend.api.analyse.repository.DensityRepository;
import com.angio.angiobackend.api.analyse.service.DensityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service("densityService")
public class DensityServiceImpl implements DensityService {


    private final DensityRepository densityRepository;

    @Autowired
    public DensityServiceImpl(DensityRepository densityRepository) {
        this.densityRepository = densityRepository;
    }

    @Override
    public Set<DensityEntity> addNewDensities(Set<DensityEntity> densities) {
        return StreamSupport
                .stream(densityRepository.saveAll(densities).spliterator(), false)
                .collect(Collectors.toSet());
    }
}
