package com.angio.app.test;

import com.angio.app.analyse.entities.AnalyseInfoEntity;
import com.angio.app.analyse.services.AnalyseInfoService;
import com.angio.app.analyse.entities.PatientEntity;
import com.angio.app.analyse.services.PatientExistsException;
import com.angio.app.analyse.services.PatientService;
import com.angio.app.analyse.requests.*;
import com.angio.app.analyse.responses.CheckPatientResponse;
import com.angio.app.analyse.responses.PatientResponse;
import com.angio.app.util.image.ImageOperation;
import com.angio.app.security.services.UserService;
import com.angio.app.userinfo.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AnonymousController() {

    }

    @RequestMapping("/api/v1")
    public ModelAndView index() {
        return new ModelAndView("forward:/greeting");
    }

    @RequestMapping("/api/v1/greeting")
    public Greeting greeting() {
        return new Greeting("Greetings for anonymous!");
    }
}
