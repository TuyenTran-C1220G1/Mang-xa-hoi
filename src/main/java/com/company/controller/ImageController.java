package com.company.controller;

import com.company.model.Image;
import com.company.model.Post;
import com.company.model.Relationship;
import com.company.model.User;
import com.company.service.IImageService;
import com.company.service.IRelationshipService;
import com.company.service.IUserService;
import com.company.service.impl.PostService;
import com.company.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ImageController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    IRelationshipService relationshipServiceImpl;

    @Autowired
    PostService postService;

    @GetMapping("/timeline-photos")
    public ModelAndView timeline_photos(Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("timeline-photos");
        modelAndView.addObject("user", user);
        Iterable<Post> posts = postService.findAllByUserId(user.getId());
        Set<Image> images = new HashSet<>();
        for (Post p: posts) {
            for (Image img: p.getImages()) {
                images.add(img);
            }
        }

        modelAndView.addObject("images", images);

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        return modelAndView;
    }

}
