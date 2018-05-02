package com.angio.app.test;

import com.angio.app.analyse.analyse_info.entities.AnalyseInfoEntity;
import com.angio.app.analyse.analyse_info.services.AnalyseInfoService;
import com.angio.app.analyse.patient.entities.PatientEntity;
import com.angio.app.analyse.patient.services.PatientExistsException;
import com.angio.app.analyse.patient.services.PatientService;
import com.angio.app.analyse.patient.services.PatientServiceImpl;
import com.angio.app.analyse.request.*;
import com.angio.app.analyse.response.CheckPatientResponse;
import com.angio.app.analyse.response.PatientResponse;
import com.angio.app.image.ImageOperation;
import com.angio.app.security.services.UserService;
import com.angio.app.userinfo.services.UserInfoService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@RestController
public class AnonymousController {
    private final AnalyseInfoService analyseInfoService;
    private final UserInfoService userInfoService;
    private final UserService userService;
    private final PatientService patientService;

    @Autowired
    public AnonymousController(AnalyseInfoService analyseInfoService, UserInfoService userInfoService,
                               UserService userService, PatientService patientService) {
        this.analyseInfoService = analyseInfoService;
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.patientService = patientService;
    }

    @RequestMapping("/api/v1")
    public ModelAndView index() {
        return new ModelAndView("forward:/greeting");
    }

    @RequestMapping("/api/v1/greeting")
    public Greeting greeting() {
        return new Greeting("Greetings for anonymous!");
    }

    @RequestMapping(path = "/api/v1/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAnalyses(){
        JSONArray jsonArray = new JSONArray();
        try {
            List<AnalyseInfoEntity> analyses = analyseInfoService.getAllBaseAnalyseInfo();
            for (int i = 0; i < analyses.size(); i++){
                AnalyseInfoEntity analyseInfoEntity = analyses.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", analyseInfoEntity.getId());
                jsonObject.put("name", analyseInfoEntity.getName());
                jsonObject.put("short_description", analyseInfoEntity.getShort_description());
                jsonObject.put("analyse_type", analyseInfoEntity.getAnalyse_type());
                jsonObject.put("patient", analyseInfoEntity.getPatient().getLastname() + " " +
                        analyseInfoEntity.getPatient().getFirstname() + " " +
                        analyseInfoEntity.getPatient().getPatronymic());
                jsonObject.put("policy", analyseInfoEntity.getPatient().getPolicy());
                jsonObject.put("diagnost", userInfoService.findByUser(analyseInfoEntity.getUser()).getLastname() + " " +
                        userInfoService.findByUser(analyseInfoEntity.getUser()).getFirstname().charAt(0));
                jsonObject.put("date", analyseInfoEntity.getAnalyse_date());
                jsonObject.put("is_analyse_finished", analyseInfoEntity.isFinished());
                jsonArray.put(jsonObject);
            }
            return ResponseEntity.ok().body(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value="/api/v1/new", method = RequestMethod.POST)
    public @ResponseBody String startNewAnalyse(@RequestBody NewAnalyseRequest newAnalyseRequest){
        try {
            PatientRequest patientRequest = newAnalyseRequest.getPatient();
            AnalyseInfoRequest analyseInfoRequest = newAnalyseRequest.getInfo();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
            Date bDay = sdf.parse(patientRequest.getBday());

            AnalyseInfoEntity newAnalyse = analyseInfoService.addNewAnalyse(userService.findByUsername(newAnalyseRequest.getUsername()),
                    new PatientEntity(patientRequest.getFirstname(),
                            patientRequest.getLastname(),
                            patientRequest.getPatronymic(),
                            patientRequest.getEmail(),
                            patientRequest.getPhone(),
                            new Timestamp(bDay.getTime()),
                            patientRequest.getPhone(),
                            patientRequest.getWork(),
                            patientRequest.getComments(),
                            patientRequest.getPolicy()),
                    analyseInfoRequest.getName(),
                    analyseInfoRequest.getShort_description(),
                    analyseInfoRequest.getFull_description(),
                    analyseInfoRequest.getAnalyse_type(),
                    analyseInfoRequest.getComments(),
                    analyseInfoRequest.getImg());
            return "successfull started new analyse. Id of new analyse: " + newAnalyse.getId();
        } catch(Exception e) {
            return "error = " + e;
        }
    }

    @RequestMapping(value="/api/v1/check_policy", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> checkPatientByPolicy(@RequestBody PolicyRequest policyRequest){
        CheckPatientResponse checkPatientResponse = new CheckPatientResponse();
        try {
            PatientEntity patientEntity = patientService.getPatientByPolicy(policyRequest.getPolicy());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
            String date = sdf.format(patientEntity.getBday());

            checkPatientResponse.setContains(true);
            checkPatientResponse.setPatient(new PatientResponse(patientEntity.getFirstname(),
                    patientEntity.getLastname(),
                    patientEntity.getPatronymic(),
                    patientEntity.getEmail(),
                    patientEntity.getPhone(),
                    patientEntity.getPolicy(),
                    date,
                    patientEntity.getLocation_address(),
                    patientEntity.getWork_address(),
                    patientEntity.getComment()));

            return ResponseEntity.ok().body(checkPatientResponse);
        } catch (PatientExistsException ex){
            checkPatientResponse.setContains(false);
            checkPatientResponse.setPatient(null);

            return ResponseEntity.ok().body(checkPatientResponse);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @RequestMapping(value="/api/v1/upload_image", method = RequestMethod.POST)
    public @ResponseBody String uploadImage2(@RequestBody ImageRequest imageRequest, HttpServletRequest request) {
        try {
            return "successfull saved: " + new ImageOperation().save(imageRequest.getImage());
        } catch(Exception e) {
            return "error = " + e;
        }
    }
}
