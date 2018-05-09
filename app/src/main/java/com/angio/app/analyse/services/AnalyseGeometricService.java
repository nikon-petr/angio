package com.angio.app.analyse.services;

import com.angio.app.analyse.entities.AnalyseGeometricEntity;
import com.angio.app.analyse.entities.AnalyseInfoEntity;
import com.angio.app.analyse.entities.VesselEntity;

import java.util.List;

public interface AnalyseGeometricService {
    AnalyseGeometricEntity getAnalyseGeometric(AnalyseInfoEntity analyseInfoEntity) throws Exception;
    List<VesselEntity> getVessels(AnalyseGeometricEntity analyseGeometricEntity) throws Exception;
    void deleteVessel(long id) throws Exception;
}