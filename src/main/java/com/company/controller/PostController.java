package com.company.controller;

import com.company.model.Image;
import com.company.model.Post;
import com.company.model.PostForm;
import com.company.model.Relationship;
import com.company.model.User;
import com.company.service.IImageService;
import com.company.service.IPostService;
import com.company.service.IRelationshipService;
import com.company.service.impl.PostService;
import com.company.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class PostController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    IPostService postService;

    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    IImageService iImageService;

    @Autowired
    IRelationshipService relationshipServiceImpl;

    @PostMapping("/createpost")
    public ModelAndView createPost(Principal principal, @ModelAttribute PostForm postForm){
        User user = userService.findByUsername(principal.getName());
        int likes = 0;
        LocalDateTime time = LocalDateTime.now();
        Set<Image> imageSet = new HashSet<>();
        List<MultipartFile> multipartFiles = postForm.getImages();
        List<String> listFileName = new ArrayList<>();
        for (MultipartFile multipartfiles: multipartFiles
             ) {
            listFileName.add(multipartfiles.getOriginalFilename());
        }
        try {
            for (int i = 0; i < multipartFiles.size(); i++) {
                FileCopyUtils.copy(multipartFiles.get(i).getBytes(), new File(fileUpload + listFileName.get(i)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < listFileName.size(); i++) {
            Image image = new Image();
            image.setImage_link(listFileName.get(i));
            imageSet.add(image);
        }
        for (Image image: imageSet
             ) {
            iImageService.save(image);
        }

        Post post = new Post(postForm.getTitle(),postForm.getContent(),time,user,imageSet, likes,postForm.getStatus());
        postService.save(post);
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/edit-post-form/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("timeline-edit-post");
            modelAndView.addObject("optionalPost", optionalPost.get());

            // lay ra list loi moi ket ban
            List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user,1);
            modelAndView.addObject("requestFriendList", requestFriendListRelationship);


            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/404-error");
            return modelAndView;
        }
    }

    @PostMapping("/edit-post")
    public ModelAndView updateCustomer(@ModelAttribute("post") Post optionalPost) {
        postService.save(optionalPost);
        ModelAndView modelAndView = new ModelAndView("timeline-edit-post");
        modelAndView.addObject("optionalPost", optionalPost);
        modelAndView.addObject("message", "Post updated successfully");
        return modelAndView;
    }

}
