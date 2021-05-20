package com.company.controller;

import com.company.model.Relationship;
import com.company.model.User;
import com.company.service.IRelationshipService;
import com.company.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class FriendController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    IRelationshipService relationshipServiceImpl;


    @GetMapping("/detailProfileFriend/{id}")
    public ModelAndView about(@PathVariable Long id, Principal principal) {
        int status = 2;
        User user = userServiceImpl.findByUsername(principal.getName());
        List<Relationship> userFriendRelationshipList = relationshipServiceImpl.findAllByUserAndStatus(user, status);
        List<Relationship> userRelationshipList = relationshipServiceImpl.findAllByUserFriendAndStatus(user, status);
        ModelAndView modelAndView = new ModelAndView("aboutFriend");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userFriendRelationshipList", userFriendRelationshipList);
        modelAndView.addObject("userRelationshipList", userRelationshipList);

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user, 1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);
        // lay ra user friend
        Optional<User> userFriend = userServiceImpl.findById(id);
        modelAndView.addObject("userFriend", userFriend.get());

        return modelAndView;
    }
}