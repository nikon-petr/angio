package com.angio.app.analyse.services;

import com.angio.app.analyse.entities.AnalyseInfoEntity;
import com.angio.app.analyse.entities.PatientEntity;
import com.angio.app.analyse.requests.AnalyseInfoRequest;
import com.angio.app.security.entities.UserEntity;

import java.util.List;

public interface AnalyseInfoService {
    List<AnalyseInfoEntity> getAllBaseAnalyseInfo() throws Exception;
    AnalyseInfoEntity addNewAnalyse(UserEntity user, PatientEntity patient, AnalyseInfoRequest analyseInfoRequest) throws Exception;
}