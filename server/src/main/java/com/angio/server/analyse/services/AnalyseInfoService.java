package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.requests.NewAnalyseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mathworks.toolbox.javabuilder.MWException;

public interface AnalyseInfoService {
    Page<AnalyseInfoEntity> listAllByPageAndSortAndFilter(Pageable pageable, String search, String date) throws Exception;
    AnalyseInfoEntity addNewAnalyseInfo(NewAnalyseRequest newAnalyseRequest) throws Exception;
    AnalyseInfoEntity startNewAnalyse(long id) throws Exception;
    AnalyseInfoEntity getAnalyseInfoById(long id) throws Exception;
    AnalyseInfoEntity updateAnalyseInfoConclusion(long id, String conclusion) throws Exception;
    void deleteAnalyse(long id) throws Exception;
    long getCountOfAnalyses(String search, String date) throws Exception;
}