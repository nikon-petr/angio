package com.angio.app.analyse.services;

import com.angio.app.analyse.entities.AnalyseGeometricEntity;
import com.angio.app.analyse.entities.AnalyseInfoEntity;
import com.angio.app.analyse.entities.VesselEntity;
import com.angio.app.analyse.repositories.AnalyseGeometricCrudRepository;
import com.angio.app.analyse.repositories.AnalyseInfoCrudRepository;
import com.angio.app.analyse.repositories.VesselCrudRepository;
import com.angio.app.util.image.ImageOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("analyseGeometricService")
@Transactional
public class AnalyseGeometricServiceImpl implements AnalyseGeometricService{
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
        vesselCrudRepository.delete(id);
    }
}