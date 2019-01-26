package com.angio.angiobackend.api.analyse;

 import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
 import com.angio.angiobackend.api.analyse.exception.PatientExistsException;
import com.angio.angiobackend.api.analyse.requests.IdRequest;
import com.angio.angiobackend.api.analyse.requests.NewAnalyseRequest;
import com.angio.angiobackend.api.analyse.requests.UpdateAnalyseInfoConclusionRequest;
import com.angio.angiobackend.api.analyse.responses.AnalyseInfoConclusionResponse;
 import com.angio.angiobackend.api.analyse.responses.CheckPatientResponse;
 import com.angio.angiobackend.api.analyse.responses.IdResponse;
import com.angio.angiobackend.api.analyse.responses.PatientResponse;
 import com.angio.angiobackend.api.analyse.service.AnalyseInfoService;
import com.angio.angiobackend.api.analyse.service.PatientService;
import com.angio.angiobackend.api.user.services.UserInfoService;
 import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
 import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(description = "Angio Analyses resource (version 1)")
public class AnalyseController {

    private final AnalyseInfoService analyseInfoService;
    private final UserInfoService userInfoService;
    private final PatientService patientService;

    @Deprecated
    @RequestMapping(path = "/api/v1/analyse", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAnalyses(@RequestParam(value = "search", required = false) String search,
                                            @RequestParam(value = "date", required = false) String date,
                                            Pageable pageable) {
        return null;
//        try {
//            Page<AnalyseInfoEntity> analyses = analyseInfoService.listAllByPageAndSortAndFilter(pageable, search, date);
//            long countOfAll = analyseInfoService.getCountOfAnalyses(search, date);
//            List<AnalyseResponse> analyseResponses = new ArrayList<>();
//            for (AnalyseInfoEntity analyse : analyses.getContent()) {
//                analyseResponses.add(new AnalyseResponse(analyse,
//                        userInfoService.findByUser(analyse.getUser()).getFullName().getFullNameString()));
//            }
//
//            AnalyseResponseFull analyseResponseFull = new AnalyseResponseFull(analyseResponses, countOfAll);
//
//            return ResponseEntity.ok().body(analyseResponseFull);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.unprocessableEntity().body(null);
//        }
    }

    @RequestMapping(value = "/api/v1/analyse/info", method = RequestMethod.POST)
    public ResponseEntity<?> addNewAnalyseInfo(@RequestBody NewAnalyseRequest newAnalyseRequest) {
        try {

            AnalyseEntity analyseEntity = analyseInfoService.addNewAnalyseInfo(newAnalyseRequest);

            return ResponseEntity.ok().body(new IdResponse(analyseEntity.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value = "/api/v1/analyse", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<?>> startNewAnalyse(@RequestBody IdRequest idRequest) {
        log.info("startNewAnalyse() - start");
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>(30 * 60 * 1000L);

        Thread analyseThread = new Thread(() -> {
            try {
                analyseInfoService.startNewAnalyse(idRequest.getId());
                result.setResult(ResponseEntity.noContent().build());
                log.info("analyseThread - analyse completed successfully");
            } catch (Exception e) {
                log.error("analyseThread - exception thrown during async task execution:", e);
                result.setResult(ResponseEntity.unprocessableEntity().body(idRequest.getId()));
            }
        });

        log.info("startNewAnalyse() - run analyse thread");
        analyseThread.run();

        log.info("startNewAnalyse() - end");
        return result;
    }

    @RequestMapping(value = "/api/v1/analyse/policy-exists/{policy}", method = RequestMethod.GET)
    public ResponseEntity<?> checkPatientByPolicy(@PathVariable("policy") String policy) {
        CheckPatientResponse checkPatientResponse = new CheckPatientResponse();
        try {
            checkPatientResponse.setExists(true);
            checkPatientResponse.setPatient(new PatientResponse(patientService.getPatientByPolicy(policy)));

            return ResponseEntity.ok().body(checkPatientResponse);
        } catch (PatientExistsException ex) {
            ex.printStackTrace();

            checkPatientResponse.setExists(false);
            checkPatientResponse.setPatient(null);

            return ResponseEntity.ok().body(checkPatientResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value = "/api/v1/analyse/detail/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> detailAnalyse(@PathVariable("id") long id, HttpServletRequest request) {
        return null;
//        try {
//            AnalyseInfoEntity analyseInfoEntity = analyseInfoService.getAnalyseInfoById(id);
//            PatientEntity patientEntity = analyseInfoEntity.getPatient();
//            AnalyseGeometricEntity analyseGeometricEntity = analyseGeometricService.getAnalyseGeometric(
//                    analyseInfoEntity);
//            List<VesselEntity> vessels = analyseGeometricService.getVessels(analyseGeometricEntity);
//
//            AnalyseBloodFlowEntity analyseBloodFlowEntity = analyseInfoEntity.getAnalyseBloodFlow();
//            String ishemiaImageFilePath = UrlUtil.getBaseUrl(
//                    request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + analyseBloodFlowEntity.getIschemiaImage();
//            String densityImageFilePath = UrlUtil.getBaseUrl(
//                    request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + analyseBloodFlowEntity.getDensityImage();
//
//            String originalImageFilePath = UrlUtil.getBaseUrl(
//                    request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + analyseInfoEntity.getImg();
//            analyseInfoEntity.setImg(originalImageFilePath);
//
//            String binarizedImageFilePath = UrlUtil.getBaseUrl(
//                    request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + analyseGeometricEntity.getBinarizedImage();
//            String skelImageFilePath = UrlUtil.getBaseUrl(
//                    request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + analyseGeometricEntity.getSkeletonizedImage();
//            analyseGeometricEntity.setBinarizedImage(binarizedImageFilePath);
//            analyseGeometricEntity.setSkeletonizedImage(skelImageFilePath);
//
//            for (VesselEntity v : analyseGeometricEntity.getVessels()) {
//                String vesselImageFilePath = UrlUtil.getBaseUrl(
//                        request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + v.getVesselImage();
//                String vesselMainImageFilePath = UrlUtil.getBaseUrl(
//                        request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + v.getMainVesselImage();
//                v.setVesselImage(vesselImageFilePath);
//                v.setMainVesselImage(vesselMainImageFilePath);
//            }
//
//            DetailAnalyseResponse detailAnalyseResponse = new DetailAnalyseResponse(
//                    patientEntity,
//                    analyseInfoEntity,
//                    analyseGeometricEntity,
//                    vessels,
//                    analyseInfoEntity.getUser().getUsername(),
//                    analyseBloodFlowEntity,
//                    ishemiaImageFilePath,
//                    densityImageFilePath);
//
//            return ResponseEntity.ok().body(detailAnalyseResponse);
//        } catch (Exception e) {
//            return ResponseEntity.unprocessableEntity().body(null);
//        }
    }

    @RequestMapping(value = "/api/v1/analyse/detail/conclusion", method = RequestMethod.POST)
    public ResponseEntity<?> updateConclusion(@RequestBody UpdateAnalyseInfoConclusionRequest request) {
        try {
            AnalyseEntity analyseEntity = analyseInfoService.updateAnalyseInfoConclusion(request.getId(),
                    request.getConclusion());

            AnalyseInfoConclusionResponse analyseInfoConclusionResponse = new AnalyseInfoConclusionResponse(
                    analyseEntity.getConclusion());

            return ResponseEntity.ok().body(analyseInfoConclusionResponse);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value = "/api/v1/analyse/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAnalyse(@PathVariable("id") long id) {
        try {
            analyseInfoService.deleteAnalyse(id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

//    @RequestMapping(value = "/api/v1/analyse/vessel/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<?> deleteVessel(@PathVariable("id") long id) {
//        try {
//            analyseGeometricService.deleteVessel(id);
//
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.unprocessableEntity().body(null);
//        }
//    }

    @RequestMapping(value = "/api/v1/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "filename", required = true) String filename) throws
            IOException {
        ClassPathResource imgFile = new ClassPathResource("static/images/analyses/" + filename);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }
}