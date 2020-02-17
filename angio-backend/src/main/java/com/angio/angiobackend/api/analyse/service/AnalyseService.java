package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.AnalyseActions;
import com.angio.angiobackend.api.analyse.dto.AdditionalInfoDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseReportDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import com.angio.angiobackend.api.analyse.dto.StarredAnalyseDto;
import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface AnalyseService {

    DetailedAnalyseDto createAnalyse(DetailedAnalyseDto dto);

    void saveExecutedAnalyse(AnalyseJmsDto dto);

    Page<AnalyseShortItemDto> filterAnalysesByQueryString(
            String search,
            Date singleDate,
            Date startDate,
            Date endDate,
            AnalyseStatusType[] statuses,
            Boolean isStarred,
            Pageable pageable);

    DetailedAnalyseDto getAnalyseById(Long id);

    AnalyseReportDto getAnalyseReport(Long id);

    DetailedAnalyseDto deleteAnalyse(Long id);

    DetailedAnalyseDto executeAction(Long id, AnalyseActions action);

    DetailedAnalyseDto updateAnalyseAdditionalInfo(Long id, AdditionalInfoDto dto);

    StarredAnalyseDto getStarredAnalyse(Long id);

    StarredAnalyseDto starAnalyse(Long id, StarredAnalyseDto starredAnalyse);

    DetailedAnalyseDto deleteGeometricAnalyseVessel(Long analyseId, Long vesselId);

    byte[] createArchive(DetailedAnalyseDto dto);

    int purgeAnalysesInStatusDeleted();
}
