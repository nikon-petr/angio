package com.angio.angiobackend.api.dashboard.service.impl;

import com.angio.angiobackend.api.analyse.repository.AnalyseRepository;
import com.angio.angiobackend.api.analyse.specification.AnalyseSpecification;
import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import com.angio.angiobackend.api.dashboard.dto.AnalyseStatisticsDto;
import com.angio.angiobackend.api.dashboard.dto.DashboardDto;
import com.angio.angiobackend.api.dashboard.dto.OrganizationStatisticsDto;
import com.angio.angiobackend.api.dashboard.dto.StatisticsDto;
import com.angio.angiobackend.api.dashboard.dto.UserStatisticsDto;
import com.angio.angiobackend.api.dashboard.service.DashboardService;
import com.angio.angiobackend.api.organization.repository.OrganizationRepository;
import com.angio.angiobackend.api.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final AnalyseRepository analyseRepository;
    private final AnalyseSpecification analyseSpecification;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('DASHBOARD_VIEW')")
    public DashboardDto getDashboard() {

        log.debug("getDashboard() - start");

        log.debug("getDashboard() - calculate statistics");
        DashboardDto dashboard = new DashboardDto()
                .setStatistics(calculateStatistics());

        log.debug("getDashboard() - end");
        return dashboard;
    }

    private StatisticsDto calculateStatistics() {
        log.debug("calculateStatistics() - start");
        StatisticsDto statistics = new StatisticsDto();

        calculateAnalyseStatistics(statistics);

        calculateUserStatistics(statistics);

        calculateOrganizationStatistics(statistics);

        log.debug("calculateStatistics() - end");
        return statistics;
    }

    private void calculateAnalyseStatistics(StatisticsDto dto) {
        log.debug("calculateAnalyseStatistics() - start");
        log.debug("calculateAnalyseStatistics() - count total");
        long totalCount = analyseRepository.count(analyseSpecification.notDeleted());
        log.debug("calculateAnalyseStatistics() - count success");
        long successCount = analyseRepository.count(analyseSpecification.inStatus(AnalyseStatusType.SUCCESS));
        log.debug("calculateAnalyseStatistics() - count failure");
        long failedCount = analyseRepository.count(analyseSpecification.inStatus(AnalyseStatusType.FAILED));
        dto.setAnalyse(new AnalyseStatisticsDto()
                .setTotalCount(totalCount)
                .setSuccessCount(successCount)
                .setSuccessPercent(calculatePercentFor(totalCount, successCount))
                .setFailedCount(failedCount)
                .setFailedPercent(calculatePercentFor(totalCount, failedCount)));
        log.debug("calculateAnalyseStatistics() - end");
    }


    private void calculateUserStatistics(StatisticsDto statistics) {
        log.debug("calculateUserStatistics() - start");
        log.debug("calculateUserStatistics() - count total");
        long totalCount = userRepository.count();
        statistics.setUser(new UserStatisticsDto()
                .setTotalCount(totalCount));
        log.debug("calculateUserStatistics() - end");
    }

    private void calculateOrganizationStatistics(StatisticsDto statistics) {
        log.debug("calculateOrganizationStatistics() - start");
        log.debug("calculateOrganizationStatistics() - count total");
        long totalCount = organizationRepository.count();
        statistics.setOrganization(new OrganizationStatisticsDto()
                .setTotalCount(totalCount));
        log.debug("calculateOrganizationStatistics() - end");
    }

    private double calculatePercentFor(long total, long part) {
        return part == 0 ? 0 : (double) part / (double) total * 100;
    }
}
