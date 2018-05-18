package com.angio.server.analyse;

import com.angio.server.analyse.entities.*;
import com.angio.server.analyse.requests.*;
import com.angio.server.analyse.responses.*;
import com.angio.server.analyse.services.AnalyseGeometricService;
import com.angio.server.analyse.services.AnalyseInfoService;
import com.angio.server.analyse.services.PatientExistsException;
import com.angio.server.analyse.services.PatientService;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.user.services.UserInfoService;
import com.angio.server.util.url.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AnalyseController {

    private final AnalyseInfoService analyseInfoService;
    private final UserInfoService userInfoService;
    private final PatientService patientService;
    private final AnalyseGeometricService analyseGeometricService;

    @Autowired
    public AnalyseController(AnalyseInfoService analyseInfoService, UserInfoService userInfoService, PatientService patientService,
                             AnalyseGeometricService analyseGeometricService) {
        this.analyseInfoService = analyseInfoService;
        this.userInfoService = userInfoService;
        this.patientService = patientService;
        this.analyseGeometricService = analyseGeometricService;
    }

    @RequestMapping(path = "/api/v1/analyse", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAnalyses() {
        try {
            List<AnalyseInfoEntity> analyses = analyseInfoService.getAllBaseAnalyseInfo();
            List<AnalyseResponse> analyseResponses = new ArrayList<>();
            for (AnalyseInfoEntity analyse : analyses) {
                analyseResponses.add(new AnalyseResponse(analyse,
                        userInfoService.findByUser(analyse.getUser()).getLastname() + " " +
                                userInfoService.findByUser(analyse.getUser()).getFirstname()));
            }
            return ResponseEntity.ok().body(analyseResponses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value="/api/v1/analyse/info", method = RequestMethod.POST)
    public ResponseEntity<?> addNewAnalyseInfo(@RequestBody NewAnalyseRequest newAnalyseRequest){
        try {
            PatientRequest patientRequest = newAnalyseRequest.getPatient();
            AnalyseInfoRequest analyseInfoRequest = newAnalyseRequest.getInfo();

            UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AnalyseInfoEntity analyseInfoEntity = analyseInfoService.addNewAnalyseInfo(userEntity, new PatientEntity(patientRequest), analyseInfoRequest);

            return ResponseEntity.ok().body(new IdResponse(analyseInfoEntity.getId()));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value = "/api/v1/analyse", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<?>> startNewAnalyse(@RequestBody IdRequest idRequest) {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>(30 * 60 * 1000L);

        new Thread(() -> {
            try {
                analyseInfoService.startNewAnalyse(idRequest.getId());
                result.setResult(ResponseEntity.noContent().build());
            } catch (Exception e) {
                result.setResult(ResponseEntity.unprocessableEntity().body(idRequest.getId()));
            }
        }).start();

        return result;
    }

    //  TODO change method type to GET
    @RequestMapping(value="/api/v1/analyse/policy-exists", method = RequestMethod.POST)
    public ResponseEntity<?> checkPatientByPolicy(@RequestBody PolicyRequest policyRequest){
        CheckPatientResponse checkPatientResponse = new CheckPatientResponse();
        try {
            checkPatientResponse.setExists(true);
            checkPatientResponse.setPatient(new PatientResponse(patientService.getPatientByPolicy(policyRequest.getPolicy())));

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

    //  TODO change method type to GET
    @RequestMapping(value = "/api/v1/analyse/detail", method = RequestMethod.POST)
    public ResponseEntity<?> detailAnalyse(@RequestBody AnalyseInfoIdRequest analyseInfoIdRequest, HttpServletRequest request) {
        try {
            AnalyseInfoEntity analyseInfoEntity = analyseInfoService.getAnalyseInfoEntity(analyseInfoIdRequest.getId());
            PatientEntity patientEntity = analyseInfoEntity.getPatient();
            AnalyseGeometricEntity analyseGeometricEntity = analyseGeometricService.getAnalyseGeometric(analyseInfoEntity);
            List<VesselEntity> vessels = analyseGeometricService.getVessels(analyseGeometricEntity);

            AnalyseBloodFlowEntity analyseBloodFlowEntity = analyseInfoEntity.getAnalyseBloodFlow();
            String ishemiaImageFilePath = UrlUtil.getBaseUrl(request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + analyseBloodFlowEntity.getIshemiaImageFileName();
            String densityImageFilePath = UrlUtil.getBaseUrl(request) + UrlUtil.ANALYSE_IMAGE_CONTEXT_PATH + analyseBloodFlowEntity.getDensityImageFileName();

            DetailAnalyseResponse detailAnalyseResponse = new DetailAnalyseResponse(
                    patientEntity,
                    analyseInfoEntity,
                    analyseGeometricEntity,
                    vessels,
                    analyseInfoEntity.getUser().getUsername(),
                    analyseBloodFlowEntity,
                    ishemiaImageFilePath,
                    densityImageFilePath);

            return ResponseEntity.ok().body(detailAnalyseResponse);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    //  TODO change method type to POST
    @RequestMapping(value = "/api/v1/analyse/detail/conclusion", method = RequestMethod.PUT)
    public ResponseEntity<?> updateConclusion(@RequestBody UpdateAnalyseInfoConclusionRequest request) {
        try {
            AnalyseInfoEntity analyseInfoEntity = analyseInfoService.updateAnalyseInfoConclusion(request.getId(), request.getConclusion());

            AnalyseInfoConclusionResponse analyseInfoConclusionResponse = new AnalyseInfoConclusionResponse(analyseInfoEntity.getConclusion());

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

    @RequestMapping(value = "/api/v1/analyse/vessel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVessel(@PathVariable("id") long id) {
        try {
            analyseGeometricService.deleteVessel(id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value = "/api/v1/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "filename", required = true) String filename) throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/images/analyses/" + filename);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }
}