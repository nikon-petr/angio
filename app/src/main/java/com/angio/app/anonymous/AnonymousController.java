package com.angio.app.anonymous;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import makeImageBinarization.Class1;
import com.mathworks.toolbox.javabuilder.MWCharArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWLogicalArray;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

@RestController
public class AnonymousController {

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("forward:/greeting");
    }

    @RequestMapping("/greeting")
    public AnonymousGreeting greeting() {
        try {
            Class1 class1 = new Class1();
            MWCharArray arr = new MWCharArray("https://sun9-2.userapi.com/c840526/v840526794/1841/2RxZYCLMaJU.jpg".toCharArray());
            Object[] result = class1.makeImageBinarization(1, arr);
            return new AnonymousGreeting(result.getClass().getSimpleName());
        } catch (Exception e) {
            return new AnonymousGreeting(":-(");
        }
    }
}
