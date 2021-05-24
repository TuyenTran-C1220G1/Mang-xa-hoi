package com.company.controller;


import com.company.model.Relationship;
import com.company.model.User;
import com.company.model.UserForm;
import com.company.service.impl.AdminServiceImpl;
import com.company.service.impl.RelationshipServiceImpl;
import com.company.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
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
    public ModelAndView admin(Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        Iterable<User> users = adminService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("users", users);
        modelAndView.addObject("user", user);

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user, 1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        return modelAndView;
    }

    @PostMapping("/search-manage")
    public ModelAndView timeline_friends(@RequestParam("search") String userName,Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        Iterable<User> users = adminService.findAll();
        List<User> userList = new ArrayList<>();
        for (User u : users) {
            if (u.getUsername().toUpperCase().contains(userName.toUpperCase())) {
                userList.add(u);
            }
        }
        ModelAndView modelAndView = new ModelAndView("admin");

        modelAndView.addObject("users", userList);
        modelAndView.addObject("user", user);

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user, 1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        return modelAndView;
    }

//    @GetMapping("/delete-user/{id}")
//    public ModelAndView showDeleteForm(@PathVariable Long id) {
//        Optional<User> userOptional = adminService.findById(id);
//        if (userOptional.isPresent()) {
//            ModelAndView modelAndView = new ModelAndView("admin");
//            modelAndView.addObject("userOptional", userOptional.get());
//            return modelAndView;
//
//        } else {
//            ModelAndView modelAndView = new ModelAndView("/404-error");
//            return modelAndView;
//        }
//    }

    @GetMapping("/delete-user/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        adminService.remove(id);
        return "redirect:/admin";
    }



    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        Optional<User> editUser = userServiceImpl.findById(id);
        ModelAndView modelAndView = new ModelAndView("/editUser", "user", user);
        modelAndView.addObject("editUser",editUser.get());

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user, 1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        return modelAndView;
    }

    @Value("${file-upload}")
    private String fileUpload;

    @PostMapping("/update/{id}")
    public ModelAndView editUser(@PathVariable Long id, @ModelAttribute("editUser") UserForm userForm, Principal principal) {
        User currentUser = userServiceImpl.findByUsername(principal.getName());
        Optional<User> user = userServiceImpl.findById(id);
        MultipartFile avatar = userForm.getAvatar();
        MultipartFile backGround = userForm.getBackground();
        String fileNameAvatar = "";
        if (avatar.getSize() != 0) {
            fileNameAvatar = avatar.getOriginalFilename();
            try {
                FileCopyUtils.copy(userForm.getAvatar().getBytes(), new File(fileUpload + fileNameAvatar));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            fileNameAvatar = user.get().getAvatar();
        }

        String fileNameBackGround = "";
        if (backGround.getSize() != 0) {
            fileNameBackGround = backGround.getOriginalFilename();
            try {
                FileCopyUtils.copy(userForm.getAvatar().getBytes(), new File(fileUpload + fileNameBackGround));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            fileNameBackGround = user.get().getBackground();
        }

        User editUser = new User(user.get().getId(), userForm.getName(), user.get().getUsername(),
                user.get().getEmail(), user.get().getPassword(), user.get().getRoles(), fileNameAvatar, fileNameBackGround,
                userForm.getDescription(), userForm.getDob(), userForm.getPhone(), userForm.getCountry(), userForm.getGender());
        userServiceImpl.save(editUser);
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("user", currentUser);
//        modelAndView.addObject("message", "Update successfully!");
//
        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(currentUser, 1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        Iterable<User> users = adminService.findAll();
        modelAndView.addObject("users", users);

        return modelAndView;
    }

}
