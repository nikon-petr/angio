package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.PatientEntity;
import com.angio.server.analyse.requests.AnalyseInfoRequest;
import com.angio.server.security.entities.UserEntity;

import com.mathworks.toolbox.javabuilder.MWException;

import java.io.IOException;
import java.util.List;

public interface AnalyseInfoService {
    List<AnalyseInfoEntity> getAllBaseAnalyseInfo() throws Exception;
    AnalyseInfoEntity addNewAnalyseInfo(UserEntity user, PatientEntity patient, AnalyseInfoRequest analyseInfoRequest) throws Exception;
    AnalyseInfoEntity startNewAnalyse(long id) throws Exception;
    AnalyseInfoEntity getAnalyseInfoEntity(long id) throws Exception;
    AnalyseInfoEntity updateAnalyseInfoConclusion(long id, String conclusion) throws Exception;
    void deleteAnalyse(long id) throws Exception;
}