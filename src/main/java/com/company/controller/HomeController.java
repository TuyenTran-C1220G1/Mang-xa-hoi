package com.company.controller;

import com.company.model.*;
import com.company.service.IPostService;
import com.company.service.IRelationshipService;
import com.company.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    IRelationshipService relationshipServiceImpl;

    @Autowired
    IPostService postService;


    @GetMapping("/home")
    public ModelAndView showHome(Principal principal, Pageable pageable) {
        principal.getName();
        User user = userServiceImpl.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("newsfeed");
        modelAndView.addObject("user", user);
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
        modelAndView.addObject("noRelationshipUserList", notRelationshipListUser);

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        modelAndView.addObject("postForm",new PostForm());

        // lay tat ca post

        Page<Post> listPost = postService.findAll(pageable);
        modelAndView.addObject("listPost", listPost);


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

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

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
    public ModelAndView doRegister(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) {

        //check email trùng
        if (userServiceImpl.existsByEmail(user.getEmail())) {
            ModelAndView modelAndView = new ModelAndView("/register");
            modelAndView.addObject("errorEmail", "Email exist!!!");
            return modelAndView;
        }

        //check username trùng
        if (userServiceImpl.existsByUsername(user.getUsername())) {
            ModelAndView modelAndView = new ModelAndView("/register");
            modelAndView.addObject("errorUsername", "Username exist!!!");
            return modelAndView;
        }

        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/register");
        }

        Role role = new Role(1L, "ROLE_USER");// mặc định quyền user
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userServiceImpl.save(user);
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/timeline")
    public ModelAndView timeline(Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("timeline");
        modelAndView.addObject("user", user);

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
        modelAndView.addObject("noRelationshipUserList", notRelationshipListUser);

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);
        modelAndView.addObject("postForm",new PostForm());

        //lay ra post cua user
        modelAndView.addObject("post",postService.findAllByUserId(user.getId()));

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

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

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
        User user = userServiceImpl.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("/setting", "user", user);

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        return modelAndView;
    }

    @Value("${file-upload}")
    private String fileUpload;

    @PostMapping("/update")
    public ModelAndView editUser(@ModelAttribute("user") UserForm userForm, Principal principal) {

        principal.getName();
        User user = userServiceImpl.findByUsername(principal.getName());
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

        User editUser = new User(user.getId(), userForm.getName(), user.getUsername(),
                user.getEmail(), user.getPassword(), user.getRoles(), fileNameAvatar, fileNameBackGround,
                userForm.getDescription(), userForm.getDob(), userForm.getPhone(), userForm.getCountry(), userForm.getGender());
        userServiceImpl.save(editUser);
        ModelAndView modelAndView = new ModelAndView("setting");
        modelAndView.addObject("user", editUser);
        modelAndView.addObject("message", "Update successfully!");

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        return modelAndView;
    }

    @GetMapping("/search")
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

        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        return modelAndView;
    }
}
