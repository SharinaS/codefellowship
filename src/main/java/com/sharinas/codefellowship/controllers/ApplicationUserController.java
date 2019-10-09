package com.sharinas.codefellowship.controllers;

import com.sharinas.codefellowship.models.ApplicationUser;
import com.sharinas.codefellowship.models.ApplicationUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ApplicationUserController {
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/signup")
    public RedirectView createNewUser(String username, String password, String firstname, String lastname, String dateofbirth, String bio) {
        // actually create a user & salt and hash password
        ApplicationUser u = new ApplicationUser(username, passwordEncoder.encode(password), firstname, lastname, dateofbirth, bio);

        // save it to a database
        applicationUserRepository.save(u);
        // send user back to homepage
        return new RedirectView("/");
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/users/{id}")
    public String showSingleUser(@PathVariable long id, Principal p, Model m) {

        ApplicationUser user = applicationUserRepository.findById(id).get();
        m.addAttribute("viewedUser", user);
        //m.addAttribute("user", p.getName()); // from thymeleaf
        return "userProfile";
    }
}
