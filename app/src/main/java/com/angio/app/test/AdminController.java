package com.angio.app.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdminController {
    @RequestMapping("/admin")
    public ModelAndView index() {
        return new ModelAndView("forward:/admin/greeting");
    }

    @RequestMapping("/admin/greeting")
    public Greeting greeting() {
        return new Greeting("Greetings for admin!");
    }
}
