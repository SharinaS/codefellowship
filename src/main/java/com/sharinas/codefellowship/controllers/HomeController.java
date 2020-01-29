package com.sharinas.codefellowship.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome(Principal p, Model m) {
        // take username and use it to say hello to the user.
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        return "home";
    }
}
