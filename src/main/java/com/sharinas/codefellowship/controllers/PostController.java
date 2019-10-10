package com.sharinas.codefellowship.controllers;

import com.sharinas.codefellowship.models.ApplicationUser;
import com.sharinas.codefellowship.models.ApplicationUserRepository;
import com.sharinas.codefellowship.models.Post;
import com.sharinas.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PostController {
    // use user repository to access info about user
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    // save the post
    @Autowired
    PostRepository postRepository;


    @GetMapping("/addPost")
    public String showPostForm(Principal p, Model m){
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        return "addPost";
    }

    @PostMapping("/addPost")
    public RedirectView addPost(Principal p, String body) {
        // code involving date written with Jeff Borda
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date timeStamp = new Date();

        ApplicationUser theUser = applicationUserRepository.findByUsername(p.getName());
        Post post = new Post(body, dateFormat.format(timeStamp), theUser);
        postRepository.save(post);

        return new RedirectView("/user/" + theUser.getId());
    }
}
