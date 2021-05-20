package com.company.controller;


import com.company.model.Relationship;
import com.company.model.User;
import com.company.service.IAdminService;
import com.company.service.impl.AdminServiceImpl;
import com.company.service.impl.RelationshipServiceImpl;
import com.company.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RelationshipServiceImpl relationshipServiceImpl;

    @GetMapping("/admin")
    public ModelAndView admin() {
        Iterable<User> users = adminService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @PostMapping("/search-manage")
    public ModelAndView timeline_friends(@RequestParam("search") String userName) {
        Iterable<User> users = adminService.findAll();
        List<User> userList=new ArrayList<>();
        for (User user: users) {
            if (user.getUsername().toUpperCase().contains(userName.toUpperCase())){
                userList.add(user);
            }
        }
        ModelAndView modelAndView = new ModelAndView("admin");

        modelAndView.addObject("users", userList);
        return modelAndView;
    }

    @GetMapping("/delete-user/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<User> userOptional = adminService.findById(id);
        if (userOptional.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/admin");
            modelAndView.addObject("userOptional", userOptional.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/404-error");
            return modelAndView;
        }
    }

    @PostMapping("/delete-user")
    public String deleteCustomer(@ModelAttribute("userOptional") User user) {
        adminService.remove(user.getId());
        return "redirect:manage";
    }

}
