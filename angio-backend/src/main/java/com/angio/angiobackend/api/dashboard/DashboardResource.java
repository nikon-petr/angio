package com.angio.angiobackend.api.dashboard;

import com.angio.angiobackend.api.dashboard.dto.DashboardDto;
import com.angio.angiobackend.api.dashboard.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Dashboard", description = "Angio dashboard REST API")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v2")
public class DashboardResource {

    private final DashboardService dashboardService;

    @ApiOperation("Get dashboard data")
    @GetMapping("/dashboard")
    public DashboardDto getDashboard() {
        return dashboardService.getDashboard();
    }
}
