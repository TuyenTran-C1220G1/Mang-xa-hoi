package com.company.controller;

import com.company.model.Relationship;
import com.company.model.User;
import com.company.service.IRelationshipService;
import com.company.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
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

    @GetMapping("/search_people")
    public ModelAndView search_people(@RequestParam("people") String userName, Principal principal) {

        User user = userServiceImpl.findByUsername(principal.getName());

        // lay ra list not relationship
        List<Relationship> listRelationshipUserFriend = relationshipServiceImpl.findAllByUser(user);
        List<User> listUserRelationshipMe = new ArrayList<>();
        for (Relationship r : listRelationshipUserFriend) {
            listUserRelationshipMe.add(r.getUserFriend());
        }

        List<Relationship> listRelationshipUser = relationshipServiceImpl.findAllByUserFriend(user);
        for (Relationship r : listRelationshipUser) {
            listUserRelationshipMe.add(r.getUser());
        }

        List<User> notRelationshipListUser = userServiceImpl.findAll();
        notRelationshipListUser.remove(user);
        notRelationshipListUser.removeAll(listUserRelationshipMe);
        // Tìm trong danh sách bạn chua co moi quan he
        List<User> resultListPeople=new ArrayList<>();
        for (User u: notRelationshipListUser) {
            if (u.getUsername().toUpperCase().contains(userName.toUpperCase())){
                resultListPeople.add(u);
            }
        }

        ModelAndView modelAndView = new ModelAndView("timeline-friends2");
        modelAndView.addObject("user", user);
        modelAndView.addObject("resultListPeople", resultListPeople);

// tìm trong danh sách bạn bè
        int status = 2;
        List<Relationship> userFriendRelationshipList = relationshipServiceImpl.findAllByUserAndStatus(user,status);

        List<Relationship> list1=new ArrayList<>();
        for (Relationship relationship: userFriendRelationshipList) {
            if (relationship.getUserFriend().getUsername().toUpperCase().contains(userName.toUpperCase())){
                list1.add(relationship);
            }
        }

        List<Relationship> userRelationshipList = relationshipServiceImpl.findAllByUserFriendAndStatus(user,status);
        List<Relationship> list2=new ArrayList<>();

        for (Relationship relationship: userRelationshipList) {
            if (relationship.getUser().getUsername().toUpperCase().contains(userName.toUpperCase())){
                list2.add(relationship);
            }
        }

        modelAndView.addObject("userFriendRelationshipList", list1);
        modelAndView.addObject("userRelationshipList", list2);


        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        return modelAndView;
    }
}