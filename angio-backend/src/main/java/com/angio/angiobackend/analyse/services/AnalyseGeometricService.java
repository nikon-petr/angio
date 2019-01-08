package com.angio.angiobackend.analyse.services;

import com.angio.angiobackend.analyse.entities.AnalyseGeometricEntity;
import com.angio.angiobackend.analyse.entities.AnalyseInfoEntity;
import com.angio.angiobackend.analyse.entities.VesselEntity;

import java.util.List;

public interface AnalyseGeometricService {
    AnalyseGeometricEntity getAnalyseGeometric(AnalyseInfoEntity analyseInfoEntity) throws Exception;
    List<VesselEntity> getVessels(AnalyseGeometricEntity analyseGeometricEntity) throws Exception;
    void deleteVessel(long id) throws Exception;
}