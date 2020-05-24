export interface DashboardModel {
    statistics: Statistics;
}

export interface Statistics {
    analyse: AnalyseStatistics;
    user: UserStatistics;
    organization: OrganizationStatistics;
}

export interface AnalyseStatistics {
    totalCount: number;
    successCount: number;
    successPercent: number;
    failedCount: number;
    failedPercent: number;
}

export interface UserStatistics {
    totalCount: number;
}

export interface OrganizationStatistics {
    totalCount: number;
}
