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
import java.sql.Date;
import java.util.Optional;

@Controller
public class RelationshipController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    IRelationshipService relationshipServiceImpl;

    @GetMapping("/addFriend/{id}")
    public ModelAndView addFriend(@PathVariable Long id, Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        Optional<User> userFriend = userServiceImpl.findById(id);
        long millis = System.currentTimeMillis();
        Date createAt = new Date(millis);
        Relationship relationship = new Relationship(user, userFriend.get(), createAt, 1);
        relationshipServiceImpl.save(relationship);
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/accessFriend/{id}")
    public ModelAndView accessFriend(@PathVariable Long id, Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        Optional<Relationship> editRelationship = relationshipServiceImpl.findById(id);
        long millis = System.currentTimeMillis();
        Date createAt = new Date(millis);
        editRelationship.get().setCreatedAt(createAt);
        editRelationship.get().setStatus(2);
        relationshipServiceImpl.save(editRelationship.get());
        return new ModelAndView("redirect:/home");
    }
}
