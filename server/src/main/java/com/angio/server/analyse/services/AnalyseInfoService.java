package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.PatientEntity;
import com.angio.server.analyse.requests.AnalyseInfoRequest;
import com.angio.server.security.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mathworks.toolbox.javabuilder.MWException;

import java.io.IOException;
import java.util.List;

public interface AnalyseInfoService {
    Page<AnalyseInfoEntity> listAllByPageAndSortAndFilter(Pageable pageable, String search, String date) throws Exception;
    AnalyseInfoEntity addNewAnalyseInfo(UserEntity user, PatientEntity patient, AnalyseInfoRequest analyseInfoRequest) throws Exception;
    AnalyseInfoEntity startNewAnalyse(long id) throws Exception;
    AnalyseInfoEntity getAnalyseInfoEntity(long id) throws Exception;
    AnalyseInfoEntity updateAnalyseInfoConclusion(long id, String conclusion) throws Exception;
    void deleteAnalyse(long id) throws Exception;
    long getCountOfAnalyses(String search, String date) throws Exception;
}