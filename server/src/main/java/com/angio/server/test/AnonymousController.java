package com.angio.server.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
