package com.company.controller;

import com.company.model.Relationship;
import com.company.model.Role;
import com.company.model.User;
import com.company.model.UserForm;
import com.company.repository.IUserRepository;
import com.company.service.IRelationshipService;
import com.company.service.UserDetailServiceImpl;
import com.company.service.impl.RelationshipServiceImpl;
import com.company.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@EnableJpaRepositories("com.company")
@Controller
public class HomeController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RelationshipServiceImpl relationshipServiceImpl;


    @GetMapping("/home")
    public ModelAndView showHome(Principal principal) {
        principal.getName();
        User user = userServiceImpl.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("/newsfeed");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView about(Principal principal) {
        int status = 2;
        User user = userServiceImpl.findByUsername(principal.getName());
        List<Relationship> userFriendRelationshipList = relationshipServiceImpl.findAllByUserAndStatus(user,status);
        List<Relationship> userRelationshipList = relationshipServiceImpl.findAllByUserFriendAndStatus(user,status);
        ModelAndView modelAndView = new ModelAndView("about");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userFriendRelationshipList", userFriendRelationshipList);
        modelAndView.addObject("userRelationshipList", userRelationshipList);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView("/logout");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView modelAndView = new ModelAndView("/register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView doRegister(@ModelAttribute User user) {
        //check email trùng
        //check username trùng
        Role role = new Role(1L, "ROLE_USER");// mặc định quyền user
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userServiceImpl.save(user);
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }

    @GetMapping("/timeline")
    public ModelAndView timeline(Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("timeline");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/timeline-friends2")
    public ModelAndView timeline_friends(Principal principal) {
        int status = 2;
        User user = userServiceImpl.findByUsername(principal.getName());
        List<Relationship> userFriendRelationshipList = relationshipServiceImpl.findAllByUserAndStatus(user,status);
        List<Relationship> userRelationshipList = relationshipServiceImpl.findAllByUserFriendAndStatus(user,status);
        ModelAndView modelAndView = new ModelAndView("timeline-friends2");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userFriendRelationshipList", userFriendRelationshipList);
        modelAndView.addObject("userRelationshipList", userRelationshipList);
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView timeline_friends(@RequestParam("search") String userName, Principal principal) {
        int status = 2;
        User user = userServiceImpl.findByUsername(principal.getName());

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
        ModelAndView modelAndView = new ModelAndView("timeline-friends2");

        modelAndView.addObject("user", user);
        modelAndView.addObject("userFriendRelationshipList", list1);
        modelAndView.addObject("userRelationshipList", list2);

        return modelAndView;
    }


    @GetMapping("/timeline-photos")
    public ModelAndView timeline_photos(Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("timeline-photos");
        modelAndView.addObject("user", user);
        return modelAndView;
    }



    @GetMapping("/edit")
    public ModelAndView showEditForm(Principal principal) {
        principal.getName();
        User user = userServiceImpl.findByUsername(principal.getName());;
        ModelAndView modelAndView = new ModelAndView("/setting", "user", user);
        return modelAndView;
    }
    @Value("${file-upload}")
    private String fileUpload;
    @PostMapping("/update")
    public ModelAndView editUser(@ModelAttribute UserForm userForm,Principal principal) {
        principal.getName();
        User user = userServiceImpl.findByUsername(principal.getName());;
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
            fileNameAvatar = user.getAvatar();
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
            fileNameBackGround = user.getBackground();
        }

        User editUser = new User(user.getId(), userForm.getName(), userForm.getUsername(), userForm.getEmail(), user.getPassword(), user.getRoles(), fileNameAvatar, fileNameBackGround, userForm.getDescription(), userForm.getDob(), userForm.getPhone(), userForm.getCountry(), userForm.getGender());
        userServiceImpl.save(editUser);
        ModelAndView modelAndView = new ModelAndView("setting");
        modelAndView.addObject("user", editUser);
        modelAndView.addObject("message", "Update successfully!");
        return modelAndView;
    }


}
