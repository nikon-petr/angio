package com.angio.angiobackend.analyse.services;

import com.angio.angiobackend.analyse.entities.AnalyseInfoEntity;
import com.angio.angiobackend.analyse.requests.NewAnalyseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnalyseInfoService {
    Page<AnalyseInfoEntity> listAllByPageAndSortAndFilter(Pageable pageable, String search, String date) throws Exception;
    AnalyseInfoEntity addNewAnalyseInfo(NewAnalyseRequest newAnalyseRequest) throws Exception;
    AnalyseInfoEntity startNewAnalyse(long id) throws Exception;
    AnalyseInfoEntity getAnalyseInfoById(long id) throws Exception;
    AnalyseInfoEntity updateAnalyseInfoConclusion(long id, String conclusion) throws Exception;
    void deleteAnalyse(long id) throws Exception;
    long getCountOfAnalyses(String search, String date) throws Exception;
}