package com.sharinas.codefellowship.controllers;

import com.sharinas.codefellowship.models.ApplicationUser;
import com.sharinas.codefellowship.models.ApplicationUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ApplicationUserController {
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/signup")
    public RedirectView createNewUser(String username, String password, String firstname, String lastname, String dateofbirth, String bio) {
        // actually create a user & salt and hash password
        ApplicationUser u = new ApplicationUser(username, passwordEncoder.encode(password), firstname, lastname, dateofbirth, bio);

        // TODO: make sure username is unique. If not, redirect back to /signup/error. Then, remove bodge in applicationUser, @Column(unique = true)
        // save it to a database
        applicationUserRepository.save(u);
//        // establish autologin after signing up for an account
        Authentication authentication = new UsernamePasswordAuthenticationToken(u, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

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

    @GetMapping("/user/{id}")
    public String showSingleUser(@PathVariable long id, Principal p, Model m) {

        ApplicationUser user = applicationUserRepository.findById(id).get();
        // switch to this eventually:
        //ApplicationUser user = applicationUserRepository.getOne(id);
        m.addAttribute("viewedUser", user);
//        m.addAttribute("user", p.getName());
        m.addAttribute("username", p.getName());
        return "userProfile";
    }

    @GetMapping("/userProfile")
    public String showCurrentUser(Principal p, Model m) {
        m.addAttribute("viewedUser", applicationUserRepository.findByUsername(p.getName()));
        m.addAttribute("username", p.getName());
        return "userProfile";
    }

    @GetMapping("/users")
    public String getAllUsers(Principal p, Model m) {
        m.addAttribute("username", p.getName());
        m.addAttribute("users", applicationUserRepository.findAll());

        return "allUsers";
    }

    // === Works with form on allUsers.html, to allow current user to follower other users
    @PostMapping ("/follow")
    public RedirectView followAUser(Principal p, long followUser) {

        ApplicationUser follower = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser poster = applicationUserRepository.getOne(followUser); // user to be followed by the current id logged in.

        follower.followUser(poster);
        applicationUserRepository.save(follower);

        // if logged in user is not following user, don't show option to add user. - add to getAllUsers @PathVariable long id
//        ApplicationUser otherUser = applicationUserRepository.findByUsername(p.getName());
//        m.addAttribute("otherUserId", otherUser.getId());
//
//        ApplicationUser user = applicationUserRepository.findById(id).get();
//        m.addAttribute("shouldShowFollow", !user.getUsersFollowingMe().contains(otherUser));


        return new RedirectView("/userProfile");
    }



}
