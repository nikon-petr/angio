package com.angio.app.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
public class AdminController {

    @RequestMapping("/admin")
    public ModelAndView index() {
        return new ModelAndView("forward:/admin/greeting");
    }

    @RequestMapping("/admin/greeting")
    public AdminGreeting greeting() {
        return new AdminGreeting("Greetings for admin!", new Date());
    }
}
