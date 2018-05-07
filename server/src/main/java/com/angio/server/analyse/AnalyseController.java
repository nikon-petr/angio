package com.angio.server.analyse;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.PatientEntity;
import com.angio.server.analyse.requests.*;
import com.angio.server.analyse.responses.AnalyseResponse;
import com.angio.server.analyse.responses.CheckPatientResponse;
import com.angio.server.analyse.responses.PatientResponse;
import com.angio.server.analyse.services.AnalyseInfoService;
import com.angio.server.analyse.services.PatientExistsException;
import com.angio.server.analyse.services.PatientService;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.userinfo.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AnalyseController {
    private final AnalyseInfoService analyseInfoService;
    private final UserInfoService userInfoService;
    private final PatientService patientService;

    @Autowired
    public AnalyseController(AnalyseInfoService analyseInfoService, UserInfoService userInfoService, PatientService patientService) {
        this.analyseInfoService = analyseInfoService;
        this.userInfoService = userInfoService;
        this.patientService = patientService;
    }

    @RequestMapping(path = "/api/v1/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAnalyses(){
        try {
            List<AnalyseInfoEntity> analyses = analyseInfoService.getAllBaseAnalyseInfo();
            List<AnalyseResponse> analyseResponses = new ArrayList<>();
            for (AnalyseInfoEntity analyse : analyses) {
                analyseResponses.add(new AnalyseResponse(analyse,
                        userInfoService.findByUser(analyse.getUser()).getLastname() + " " +
                                userInfoService.findByUser(analyse.getUser()).getFirstname()));
                System.out.print("flag: " + analyse.isFinished());
            }
            return ResponseEntity.ok().body(analyseResponses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value="/api/v1/new", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> startNewAnalyse(@RequestBody NewAnalyseRequest newAnalyseRequest){
        try {
            PatientRequest patientRequest = newAnalyseRequest.getPatient();
            AnalyseInfoRequest analyseInfoRequest = newAnalyseRequest.getInfo();

            UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            analyseInfoService.addNewAnalyse(userEntity, new PatientEntity(patientRequest), analyseInfoRequest);
            return ResponseEntity.ok().body(null);
        } catch(Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value="/api/v1/check_policy", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> checkPatientByPolicy(@RequestBody PolicyRequest policyRequest){
        CheckPatientResponse checkPatientResponse = new CheckPatientResponse();
        try {
            checkPatientResponse.setContains(true);
            checkPatientResponse.setPatient(new PatientResponse(patientService.getPatientByPolicy(policyRequest.getPolicy())));

            return ResponseEntity.ok().body(checkPatientResponse);
        } catch (PatientExistsException ex){
            checkPatientResponse.setContains(false);
            checkPatientResponse.setPatient(null);

            return ResponseEntity.ok().body(checkPatientResponse);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }
}