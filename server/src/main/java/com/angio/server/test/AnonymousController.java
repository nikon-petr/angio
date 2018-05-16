package com.angio.server.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public Greeting greeting() throws IOException {
        return new Greeting("Anonymous greeting!");
    }
}
