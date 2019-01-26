package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.analyse.requests.NewAnalyseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnalyseInfoService {
    Page<AnalyseEntity> listAllByPageAndSortAndFilter(Pageable pageable, String search, String date) throws Exception;
    AnalyseEntity addNewAnalyseInfo(NewAnalyseRequest newAnalyseRequest) throws Exception;
    AnalyseEntity startNewAnalyse(long id) throws Exception;
    AnalyseEntity getAnalyseInfoById(long id) throws Exception;
    AnalyseEntity updateAnalyseInfoConclusion(long id, String conclusion) throws Exception;
    void deleteAnalyse(long id) throws Exception;
    long getCountOfAnalyses(String search, String date) throws Exception;
}