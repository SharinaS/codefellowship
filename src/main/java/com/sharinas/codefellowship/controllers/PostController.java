package com.sharinas.codefellowship.controllers;

import com.sharinas.codefellowship.models.ApplicationUser;
import com.sharinas.codefellowship.models.ApplicationUserRepository;
import com.sharinas.codefellowship.models.Post;
import com.sharinas.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {
    // use user repository to access info about user
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    // save the post
    @Autowired
    PostRepository postRepository;

    @GetMapping("/addPost")
    public String showPostForm(){
        return "addPost";
    }

    @PostMapping("/addPost")
    public RedirectView addPost(Principal p, String body, String timeStamp, ApplicationUser owner) {
        ApplicationUser theUser = applicationUserRepository.findByUsername(p.getName());

        /* add code here about saving the post */
        Post post = new Post(body, timeStamp, owner);
        postRepository.save(post);

        return new RedirectView("/users/" + theUser.getId());
    }
}
