package com.angio.angiobackend.api.analyse;

import com.angio.angiobackend.api.analyse.dto.AdditionalInfoDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseReportDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import com.angio.angiobackend.api.analyse.dto.StarredAnalyseDto;
import com.angio.angiobackend.api.analyse.service.AnalyseService;
import com.angio.angiobackend.api.analyse.service.AnalyseZipService;
import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import com.angio.angiobackend.api.analyse.validation.group.NewAnalyse;
import com.angio.angiobackend.api.common.report.service.ReportService;
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
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import java.nio.charset.Charset;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "Analyse", description = "Angio analyse REST API")
@RequestMapping(path = "/api/v2/analyse")
public class AnalyseResource {

    private final static String ANALYSE_REPORT_TEMPLATE = "analyse.ftl";

    private final ReportService reportService;
    private final AnalyseService analyseService;
    private final AnalyseZipService analyseZipService;

    @ApiOperation(value = "Create new analyse")
    @PostMapping
    public DetailedAnalyseDto createAnalyse(@RequestBody @Validated(NewAnalyse.class) DetailedAnalyseDto analyseDto) {
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
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "singleDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date singleDate,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate,
            @RequestParam(value = "statuses", required = false) AnalyseStatusType[] statuses,
            @RequestParam(value = "isStarred", required = false) Boolean onlyStarred,
            @ApiIgnore @PageableDefault Pageable pageable) {
        return analyseService.filterAnalysesByQueryString(search, singleDate, startDate, endDate, statuses, onlyStarred, pageable);
    }

    @ApiOperation(value = "Render analyse report to pdf file", produces = MediaType.APPLICATION_PDF_VALUE)
    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> render(@PathVariable Long id) {

        AnalyseReportDto dto = analyseService.getAnalyseReport(id);
        byte[] contents = reportService.render(ANALYSE_REPORT_TEMPLATE, dto);

        String filename = String.format("analyse-report-%s-%tD.pdf", dto.getAdditionalInfo().getName(), dto.getAnalyseDate());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(filename, Charset.forName("UTF-8")).build());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Unload analyse resources archive", produces = "application/zip")
    @GetMapping("/{id}/archive")
    public ResponseEntity<byte[]> getArchive(@PathVariable Long id) {

        DetailedAnalyseDto dto = analyseService.getAnalyseById(id);
        byte[] contents = analyseZipService.createArchive(dto);

        String filename = String.format("analyse-archive-%s-%s-%tD.pdf",
                dto.getId(),
                dto.getAdditionalInfo().getName(),
                dto.getAnalyseDate());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/zip"));
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(filename, Charset.forName("UTF-8")).build());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
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
    public DetailedAnalyseDto updateAnalyseAdditionalInfo(@PathVariable Long id,
                                                          @RequestBody @Validated AdditionalInfoDto additionalInfo) {
        return analyseService.updateAnalyseAdditionalInfo(id, additionalInfo);
    }

    @ApiOperation("Get starred property for analyse for current user")
    @GetMapping("/{id}/starred")
    public StarredAnalyseDto getStarredAnalyse(@PathVariable Long id) {
        return analyseService.getStarredAnalyse(id);
    }

    @ApiOperation("Change starred property of analyse for current user")
    @PostMapping("/{id}/starred")
    public StarredAnalyseDto starAnalyse(@PathVariable Long id,
                                         @RequestBody @Validated StarredAnalyseDto starredAnalyseDto) {
        return analyseService.starAnalyse(id, starredAnalyseDto);
    }

    @ApiOperation("Delete vessel from geometric analyse by id")
    @DeleteMapping("/{analyseId}/geometric/vessel/{vesselId}")
    public DetailedAnalyseDto deleteAnalyse(@PathVariable Long analyseId, @PathVariable Long vesselId) {
        return analyseService.deleteGeometricAnalyseVessel(analyseId, vesselId);
    }

    @ApiOperation("Purge analyses in status DELETED")
    @DeleteMapping
    @PreAuthorize("hasAuthority('ANALYSE_PURGE_DELETED')")
    public Integer purgeAnalysesInStatusDeleted() {
        return analyseService.purgeAnalysesInStatusDeleted();
    }
}
