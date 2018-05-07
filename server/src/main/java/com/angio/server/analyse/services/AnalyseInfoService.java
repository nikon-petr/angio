package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.PatientEntity;
import com.angio.server.analyse.requests.AnalyseInfoRequest;
import com.angio.server.security.entities.UserEntity;

import java.util.List;

public interface AnalyseInfoService {
    List<AnalyseInfoEntity> getAllBaseAnalyseInfo() throws Exception;
    AnalyseInfoEntity addNewAnalyse(UserEntity user, PatientEntity patient, AnalyseInfoRequest analyseInfoRequest) throws Exception;
}