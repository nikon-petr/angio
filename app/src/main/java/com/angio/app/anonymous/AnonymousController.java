package com.angio.app.anonymous;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AnonymousController {

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("forward:/greeting");
    }

    @RequestMapping("/greeting")
    public AnonymousGreeting greeting() {
        return new AnonymousGreeting("Greetings for anonymous!");
    }
}
