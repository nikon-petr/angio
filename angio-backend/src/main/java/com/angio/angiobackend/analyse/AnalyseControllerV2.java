package com.angio.angiobackend.analyse;

import com.angio.angiobackend.analyse.dto.AnalyseInfoDto;
import com.angio.angiobackend.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.analyse.services.AnalyseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(description = "Angio analyse resources (version 2)")
@RequestMapping(path = "/api/v2/analyse")
public class AnalyseControllerV2 {

    private final AnalyseService analyseService;

    @ApiOperation(value = "Resource to filter analyses by query string", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Client error"),
            @ApiResponse(code = 500, message = "Server error")})
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

    @ApiOperation(value = "Resource to create new analyse", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Client error"),
            @ApiResponse(code = 500, message = "Server error")})
    @PostMapping
    public AnalyseInfoDto createAnalyse(@RequestBody AnalyseInfoDto analyseDto) {
        return analyseService.createAnalyse(analyseDto);
    }
}
