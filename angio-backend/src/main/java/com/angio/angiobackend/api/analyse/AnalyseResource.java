package com.angio.angiobackend.api.analyse;

import com.angio.angiobackend.api.analyse.dto.AdditionalInfoDto;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.service.AnalyseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "Analyse", description = "Angio analyse REST API")
@RequestMapping(path = "/api/v2/analyse")
public class AnalyseResource {

    private final AnalyseService analyseService;

    @ApiOperation(value = "Create new analyse")
    @PostMapping
    public DetailedAnalyseDto createAnalyse(@RequestBody @Validated DetailedAnalyseDto analyseDto) {
        return analyseService.createAnalyse(analyseDto);
    }

    @ApiOperation("Get analyse by id")
    @GetMapping("/{id}")
    public DetailedAnalyseDto getAnalyseById(@PathVariable Long id) {
        return analyseService.getAnalyseById(id);
    }

    @ApiOperation(value = "Filter analyses by query string")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "format: property,asc|desc")})
    @GetMapping
    public Page<AnalyseShortItemDto> filterAnalyses(
            @RequestParam(value = "queryString", required = false) String queryString,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="dd-MM-yyyy") Date date,
            @ApiIgnore @PageableDefault Pageable pageable) {
        return analyseService.filterAnalysesByQueryString(queryString, date, pageable);
    }

    @ApiOperation("Delete analyse by id")
    @DeleteMapping("/{id}")
    public DetailedAnalyseDto deleteAnalyse(@PathVariable Long id) {
        return analyseService.deleteAnalyse(id);
    }

    @ApiOperation("Execute action for analyse")
    @PostMapping("/{id}")
    public DetailedAnalyseDto executeAction(
            @PathVariable Long id,
            @ApiParam(allowEmptyValue = true) @RequestParam AnalyseActions action) {
        return analyseService.executeAction(id, action);
    }

    @ApiOperation("Update one or more analyse additional info fields")
    @PatchMapping("/{id}/additional-info")
    public DetailedAnalyseDto updateAnalyseAdditionalInfo(@PathVariable Long id, @RequestBody AdditionalInfoDto additionalInfo) {
        return analyseService.updateAnalyseAdditionalInfo(id, additionalInfo);
    }

    @ApiOperation("Delete vessel from geometric analyse by id")
    @DeleteMapping("/{analyseId}/geometric/vessel/{vesselId}")
    public DetailedAnalyseDto deleteAnalyse(@PathVariable Long analyseId, @PathVariable Long vesselId) {
        return analyseService.deleteGeometricAnalyseVessel(analyseId, vesselId);
    }

    @ApiOperation("Purge analyses in status DELETED")
    @PostMapping("/purge")
    @PreAuthorize("hasAuthority('ANALYSE_PURGE_DELETED')")
    public Integer purgeAnalysesInStatusDeleted() {
        return analyseService.purgeAnalysesInStatusDeleted();
    }
}
