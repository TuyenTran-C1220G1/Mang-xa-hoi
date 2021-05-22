package com.company.controller;

import com.company.model.*;
import com.company.service.IImageService;
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
    PostService postService;
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
            List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user, 1);
            modelAndView.addObject("requestFriendList", requestFriendListRelationship);

            modelAndView.addObject("user", user);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-post")
    public ModelAndView edit(@ModelAttribute("optionalPost") PostForm postForm, Principal principal) {
        Optional<Post> optionalPost = postService.findById(postForm.getId());
        User user = userService.findByUsername(principal.getName());
        LocalDateTime time = LocalDateTime.now();

        //neu khong sua anh thi van lay anh cu
        List<String> listFileName = new ArrayList<>();
        Set<Image> imageSet = new HashSet<>();
        List<MultipartFile> multipartFiles = postForm.getImages();
        if (multipartFiles.get(0).getSize()!=0) {
            for (MultipartFile multipartfiles : multipartFiles) {
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
            for (Image image : imageSet) {
                iImageService.save(image);
            }
        } else {
            imageSet = optionalPost.get().getImages();
        }

        Post editPost = new Post(postForm.getId(),postForm.getTitle(), postForm.getContent(), time, user, imageSet, postForm.getStatus());
        postService.save(editPost);
        ModelAndView modelAndView = new ModelAndView("timeline-edit-post");
        modelAndView.addObject("user", user);
        // lay ra list loi moi ket ban
        List<Relationship> requestFriendListRelationship = relationshipServiceImpl.findAllByUserFriendAndStatus(user, 1);
        modelAndView.addObject("requestFriendList", requestFriendListRelationship);

        modelAndView.addObject("optionalPost", editPost);
        modelAndView.addObject("message", "Post updated successfully");
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id){
        postService.remove(id);
        return "redirect:/timeline";
    }

}
