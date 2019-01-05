package com.angio.server.analyse.services;

import com.angio.server.analyse.dto.AnalyseShortItemDto;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.requests.NewAnalyseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface AnalyseInfoService {
    Page<AnalyseShortItemDto> filterAnalysesByQueryString(String queryString, Date date, Pageable pageable);
    Page<AnalyseInfoEntity> listAllByPageAndSortAndFilter(Pageable pageable, String search, String date) throws Exception;
    AnalyseInfoEntity addNewAnalyseInfo(NewAnalyseRequest newAnalyseRequest) throws Exception;
    AnalyseInfoEntity startNewAnalyse(long id) throws Exception;
    AnalyseInfoEntity getAnalyseInfoById(long id) throws Exception;
    AnalyseInfoEntity updateAnalyseInfoConclusion(long id, String conclusion) throws Exception;
    void deleteAnalyse(long id) throws Exception;
    long getCountOfAnalyses(String search, String date) throws Exception;
}