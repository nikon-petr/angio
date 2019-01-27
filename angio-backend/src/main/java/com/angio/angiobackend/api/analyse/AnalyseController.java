package com.angio.angiobackend.api.analyse;

import com.angio.angiobackend.api.analyse.dto.ConclusionDto;
import com.angio.angiobackend.api.analyse.dto.ExtendedAnalyseDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.service.AnalyseService;
import com.angio.angiobackend.api.analyse.service.impl.AnalyseServiceImpl;
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
@Api(description = "Angio analyse resources")
@RequestMapping(path = "/api/v2/analyse")
public class AnalyseController {

    private final AnalyseService analyseService;

    @ApiOperation(value = "Create new analyse")
    @PostMapping
    public ExtendedAnalyseDto createAnalyse(@RequestBody ExtendedAnalyseDto analyseDto) {
        return analyseService.createAnalyse(analyseDto);
    }

    @ApiOperation("Get analyse by id")
    @GetMapping("/{id}")
    public ExtendedAnalyseDto getAnalyseById(@PathVariable Long id) {
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
    public ExtendedAnalyseDto deleteAnalyse(@PathVariable Long id) {
        return analyseService.deleteAnalyse(id);
    }

    @ApiOperation("Send analyse to execution by id")
    @PostMapping("/{id}")
    public ExtendedAnalyseDto sendAnalyseToExecution(
            @PathVariable Long id,
            @ApiParam(allowEmptyValue = true) @RequestParam Boolean sendToExecution) {
        return analyseService.sendAnalyseToExecution(id);
    }

    //TODO: implement patch for all analyse fields
    @ApiOperation("Update one or more analyse fields")
    @PatchMapping("/{id}")
    public ExtendedAnalyseDto patchAnalyse(@PathVariable Long id, @RequestBody ConclusionDto conclusion) {
        return analyseService.patchAnalyse(id, conclusion);
    }

    @ApiOperation("Delete vessel from geometric analyse by id")
    @DeleteMapping("/{analyseId}/geometric/vessel/{vesselId}")
    public ExtendedAnalyseDto deleteAnalyse(@PathVariable Long analyseId, @PathVariable Long vesselId) {
        return analyseService.deleteGeometricAnalyseVessel(analyseId, vesselId);
    }
}
