package com.angio.server.analyse.services.impl;

import com.angio.server.analyse.entities.AnalyseGeometricEntity;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.VesselEntity;
import com.angio.server.analyse.exception.AnalyseGeometricExistsException;
import com.angio.server.analyse.repositories.AnalyseGeometricCrudRepository;
import com.angio.server.analyse.repositories.VesselCrudRepository;
import com.angio.server.analyse.services.AnalyseGeometricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("analyseGeometricService")
@Transactional
public class AnalyseGeometricServiceImpl implements AnalyseGeometricService {
    AnalyseGeometricCrudRepository analyseGeometricCrudRepository;
    VesselCrudRepository vesselCrudRepository;

    @Autowired
    public AnalyseGeometricServiceImpl(AnalyseGeometricCrudRepository analyseGeometricCrudRepository,
                                       VesselCrudRepository vesselCrudRepository){
        this.analyseGeometricCrudRepository = analyseGeometricCrudRepository;
        this.vesselCrudRepository = vesselCrudRepository;
    }

    @Override
    public AnalyseGeometricEntity getAnalyseGeometric(AnalyseInfoEntity analyseInfoEntity) throws Exception {
        AnalyseGeometricEntity analyseGeometricEntity = analyseGeometricCrudRepository.findByAnalyseInfo(analyseInfoEntity).stream()
                .findFirst()
                .orElse(null);

        if (analyseGeometricEntity == null){
            throw new AnalyseGeometricExistsException("Geometric analyse does not exist.");
        }

        return analyseGeometricEntity;
    }

    @Override
    public List<VesselEntity> getVessels(AnalyseGeometricEntity analyseGeometricEntity) throws Exception {
        return vesselCrudRepository.findByAnalyseGeometric(analyseGeometricEntity);
    }

    @Override
    public void deleteVessel(long id) throws Exception {
        vesselCrudRepository.deleteById(id);
    }
}