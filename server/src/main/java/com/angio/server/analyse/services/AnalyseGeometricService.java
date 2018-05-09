package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.AnalyseGeometricEntity;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.VesselEntity;

import java.util.List;

public interface AnalyseGeometricService {
    AnalyseGeometricEntity getAnalyseGeometric(AnalyseInfoEntity analyseInfoEntity) throws Exception;
    List<VesselEntity> getVessels(AnalyseGeometricEntity analyseGeometricEntity) throws Exception;
    void deleteVessel(long id) throws Exception;
}