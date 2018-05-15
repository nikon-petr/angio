package com.angio.server.test;

import com.angio.server.util.matlab.BloodFlowAnalyseAdapter;
import com.angio.server.util.matlab.BloodFlowAnalyseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

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

    @RequestMapping(value = "/api/v1/test-matlab", method = RequestMethod.GET)
    public BloodFlowAnalyseResponse runAnalyse() {
        try {
            String targetImageAbsolutePath;
            File sourceImageFile = new ClassPathResource("./static/images/analyses/5.bmp").getFile();
            if (!sourceImageFile.exists()) {
                throw new IOException("File not found");
            }
            BloodFlowAnalyseAdapter bloodFlowAnalyseAdapter = new BloodFlowAnalyseAdapter();
            BloodFlowAnalyseResult analyseResult =  bloodFlowAnalyseAdapter.runAnalyse(sourceImageFile.getAbsolutePath());
            return new BloodFlowAnalyseResponse(analyseResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
